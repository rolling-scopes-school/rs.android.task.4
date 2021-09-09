package com.bignerdranch.android.studentstorage.fragments

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bignerdranch.android.studentstorage.Callbacks
import com.bignerdranch.android.studentstorage.R
import com.bignerdranch.android.studentstorage.model.Student
import com.bignerdranch.android.studentstorage.viewmodel.StudentListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_student_list.*

class StudentListFragment : Fragment() {
    private lateinit var studentRecyclerView: RecyclerView
    private var adapter: StudentAdapter = StudentAdapter(emptyList())
    private val studentListViewModel: StudentListViewModel by lazy {
        ViewModelProviders.of(this).get(StudentListViewModel::class.java)
    }
    private var callbacks: Callbacks? = null
    private lateinit var menu: Menu
    private var isMayDelete: Boolean = false
    private var isUpdated: Boolean = false
    private var addingButton: FloatingActionButton? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var student: Student? = null
// TODO: fix function: deleting. And, optionally, change fragment presentation
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as? Callbacks
        studentListViewModel.sortStudents(requireNotNull(arguments?.getString(SORTING_MODE)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)
        studentRecyclerView = view.findViewById(R.id.student_recycler_view) as RecyclerView
        studentRecyclerView.layoutManager = LinearLayoutManager(context)
        studentRecyclerView.adapter = adapter

        if (savedInstanceState != null){
            isMayDelete = savedInstanceState.getBoolean(QUIT_STUDENT)
            isUpdated = savedInstanceState.getBoolean(ROOT_REFRESH)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_student_list, menu)
        this.menu = menu

        switchTrash()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        updateStudentList()
        return when (item.itemId) {
            R.id.dbms_changing -> {
                val builder = AlertDialog.Builder(requireNotNull(context))
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setTitle(R.string.titleAlertDialog)
                builder.setMessage(R.string.messageAlertDialog)

                builder.setPositiveButton(R.string.positiveButton) {
                        _, _ ->
                    // Add necessary method for changing DBMS
                }

                builder.setNegativeButton(R.string.negativeButton) {
                        _, _ ->
                    Toast.makeText(
                        context, R.string.canceledMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                val alertDialog = builder.create()
                alertDialog.show()

                val positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                positiveButton.setTextColor(Color.RED)
                val negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                negativeButton.setTextColor(Color.GRAY)
                true
            }
            R.id.delete_student -> {
                isMayDelete = !isMayDelete
                switchTrash()
                floatingActionButton.isEnabled = !isMayDelete
                true
            }
            R.id.sorting_student -> {
                callbacks?.onSortSelected()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun switchTrash(){
        menu.getItem(1).icon = ContextCompat.getDrawable(
            requireContext(),
            if (isMayDelete) R.drawable.ic_baseline_delete_forever_24
            else R.drawable.ic_baseline_delete_24
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = view.findViewById(R.id.swiperFreshLayout)
        swipeRefreshLayout?.setOnRefreshListener {
            refreshStudentList()
            swipeRefreshLayout?.isRefreshing = false
        }

        studentListViewModel.studentListLiveData.observe(
            viewLifecycleOwner,
            { students ->
                students?.let {
                    adapter.students = students
                    studentRecyclerView.adapter = adapter
                }
            }
        )

        addingButton = view.findViewById(R.id.floatingActionButton)
        addingButton?.setOnClickListener {
            updateStudentList()
            callbacks?.onCreateNewStudent()
        }
    }

    private fun updateStudentList(){
        student = arguments?.getParcelable(NEW_STUDENT) as Student?
        if (student != null && !isUpdated) {
            studentListViewModel.addStudent(requireNotNull(student))
            Toast.makeText(context, R.string.updated, Toast.LENGTH_SHORT).show()
            isUpdated = true
        }
    }

    private fun refreshStudentList(){
        callbacks?.onMainScreen(null, "name")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(ROOT_REFRESH, isUpdated)
        outState.putBoolean(QUIT_STUDENT, isMayDelete)
    }

    override fun onDestroy() {
        super.onDestroy()
        updateStudentList()
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private inner class StudentHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var student: Student
        private val nameTextView: TextView = itemView.findViewById(R.id.name_student)
        private val ageTextView: TextView = itemView.findViewById(R.id.age_student)
        private val ratingTextView: TextView = itemView.findViewById(R.id.rating_student)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(student: Student) {
            this.student = student
            nameTextView.text = this.student.name
            ageTextView.text = this.student.age.toString()
            ratingTextView.text = this.student.rating.toString()
        }

        override fun onClick(v: View) {
            if (!isMayDelete)
                callbacks?.onStudentSelected(student.id)
            else {
                studentListViewModel.deleteStudent(student)
                refreshStudentList()
            }
        }
    }

    private inner class StudentAdapter(var students: List<Student>)
        : RecyclerView.Adapter<StudentHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
            val layoutInflater = LayoutInflater.from(context)
            val view = layoutInflater.inflate(R.layout.list_item_student, parent, false)
            return StudentHolder(view)
        }

        override fun onBindViewHolder(holder: StudentHolder, position: Int) {
            val student = students[position]
            holder.bind(student)
        }

        override fun getItemCount() = students.size
    }

    companion object {
        private const val ROOT_REFRESH = "juice nice"
        private const val QUIT_STUDENT = "Expelled student"

        private const val NEW_STUDENT = "new student"
        private const val SORTING_MODE = "sorting mode"

        @JvmStatic
        fun newInstance(student: Student? = null, sortingMode: String):
                StudentListFragment = StudentListFragment().apply {
            arguments = bundleOf(NEW_STUDENT to student, SORTING_MODE to sortingMode)
        }
    }
}