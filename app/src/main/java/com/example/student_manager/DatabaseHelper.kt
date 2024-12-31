import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "StudentDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_STUDENTS = "students"

        // Columns
        private const val COLUMN_ID = "id"
        private const val COLUMN_STUDENT_ID = "student_id"
        private const val COLUMN_NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_STUDENTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_STUDENT_ID TEXT UNIQUE,
                $COLUMN_NAME TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENTS")
        onCreate(db)
    }

    fun addStudent(student: StudentModel): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_STUDENT_ID, student.studentId)
            put(COLUMN_NAME, student.studentName)
        }

        return try {
            db.insertOrThrow(TABLE_STUDENTS, null, values)
            true
        } catch (e: Exception) {
            false
        } finally {
            db.close()
        }
    }

    @SuppressLint("Range")
    fun getAllStudents(): List<StudentModel> {
        val studentList = mutableListOf<StudentModel>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_STUDENTS"

        db.rawQuery(selectQuery, null).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val studentId = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ID))
                    val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                    studentList.add(StudentModel(studentName = name, studentId = studentId))
                } while (cursor.moveToNext())
            }
        }

        db.close()
        return studentList
    }

    fun updateStudent(student: StudentModel): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, student.studentName)
        }

        return try {
            val rowsAffected = db.update(
                TABLE_STUDENTS,
                values,
                "$COLUMN_STUDENT_ID = ?",
                arrayOf(student.studentId)
            )
            rowsAffected > 0
        } catch (e: Exception) {
            false
        } finally {
            db.close()
        }
    }

    fun deleteStudent(studentId: String): Boolean {
        val db = this.writableDatabase
        return try {
            val rowsAffected = db.delete(
                TABLE_STUDENTS,
                "$COLUMN_STUDENT_ID = ?",
                arrayOf(studentId)
            )
            rowsAffected > 0
        } catch (e: Exception) {
            false
        } finally {
            db.close()
        }
    }
}