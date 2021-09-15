package rs.android.task4

import rs.android.task4.repository.Animal

const val KEY_ANIMAL = "key_animal"
const val DATABASE_NAME = "animals-database"
const val DATABASE_VERSION = 1
const val DATABASE_SOURCE_NAME_ROOM = "room"
const val DATABASE_SOURCE_NAME_CURSOR = "cursor"
const val COLUMN_NAME = "name"
const val COLUMN_AGE = "age"
const val COLUMN_BREED = "breed"
const val DEFAULT_PREF_DATABASE_NAME = "room"


object ListAnimals {
    val animals = listOf<Animal>(
        Animal(0, "Maximus", 3, "Abyssinian cat"),
        Animal(0, "Smudge", 5, "African Bush Elephant"),
        Animal(0, "Peter", 8, "American Cockier Spaniel"),
        Animal(0, "Barsik", 10, "Appenzeller Dog"),
        Animal(0, "Tixy", 4, "Cairn Terrier"),
        Animal(0, "Pantaleon", 11, "Collie"),
        Animal(0, "Felix", 8, "Greenland Dog"),
        Animal(0, "Wilberforce", 12, "Jack Russel"),
        Animal(0, "Tara", 4, "Puffin"),
        Animal(0, "Neutron", 2, "Siberian Husky"),
        Animal(0, "Henrietta", 3, "Sun Bear")
    )
}