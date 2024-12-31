package com.example.student_manager

import DatabaseHelper
import StudentModel
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController


class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    var students = mutableListOf<StudentModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo DatabaseHelper
        dbHelper = DatabaseHelper(this)

        // Set up the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        loadInitialData()
    }

    private fun loadInitialData() {
        // Kiểm tra xem database có dữ liệu chưa
        val existingStudents = dbHelper.getAllStudents()
        if (existingStudents.isEmpty()) {
            // Nếu chưa có dữ liệu, thêm dữ liệu mẫu
            val initialStudents = listOf(
                StudentModel("Nguyễn Văn An", "SV001"),
                StudentModel("Trần Thị Bảo", "SV002"),
                StudentModel("Lê Hoàng Cường", "SV003"),
                StudentModel("Phạm Thị Dung", "SV004"),
                StudentModel("Đỗ Minh Đức", "SV005"),
                StudentModel("Vũ Thị Hoa", "SV006"),
                StudentModel("Hoàng Văn Hải", "SV007"),
                StudentModel("Bùi Thị Hạnh", "SV008"),
                StudentModel("Đinh Văn Hùng", "SV009"),
                StudentModel("Nguyễn Thị Linh", "SV010"),
                StudentModel("Phạm Văn Long", "SV011"),
                StudentModel("Trần Thị Mai", "SV012"),
                StudentModel("Lê Thị Ngọc", "SV013"),
                StudentModel("Vũ Văn Nam", "SV014"),
                StudentModel("Hoàng Thị Phương", "SV015"),
                StudentModel("Đỗ Văn Quân", "SV016"),
                StudentModel("Nguyễn Thị Thu", "SV017"),
                StudentModel("Trần Văn Tài", "SV018"),
                StudentModel("Phạm Thị Tuyết", "SV019"),
                StudentModel("Lê Văn Vũ", "SV020")
            )

            initialStudents.forEach { student ->
                dbHelper.addStudent(student)
            }
            students = initialStudents.toMutableList()
        } else {
            // Nếu đã có dữ liệu, load từ database
            students = existingStudents.toMutableList()
        }
    }

    // Tạo options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    // Xử lý sự kiện options menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_new -> {
                // Điều hướng đến AddStudentFragment
                findNavController(R.id.nav_host_fragment)
                    .navigate(R.id.action_studentListFragment_to_addStudentFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun addStudent(student: StudentModel): Boolean {
        return if (dbHelper.addStudent(student)) {
            students.add(student)
            true
        } else {
            false
        }
    }

    // Cập nhật thông tin sinh viên
    fun updateStudent(originalName: String, originalId: String, updatedStudent: StudentModel) {
        if (dbHelper.updateStudent(updatedStudent)) {
            val index = students.indexOfFirst {
                it.studentName == originalName && it.studentId == originalId
            }
            if (index != -1) {
                students[index] = updatedStudent
            }
        }
    }

    // Xóa sinh viên
    fun deleteStudent(studentId: String): Boolean {
        return if (dbHelper.deleteStudent(studentId)) {
            students.removeAll { it.studentId == studentId }
            true
        } else {
            false
        }
    }

    // Lấy danh sách sinh viên
    fun refreshStudents() {
        students = dbHelper.getAllStudents().toMutableList()
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}