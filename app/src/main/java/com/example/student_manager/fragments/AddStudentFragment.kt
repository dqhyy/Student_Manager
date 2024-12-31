package com.example.student_manager.fragments

import StudentModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.student_manager.MainActivity
import com.example.student_manager.R

class AddStudentFragment : Fragment() {

    private lateinit var editTextName: EditText
    private lateinit var editTextId: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ánh xạ các view
        editTextName = view.findViewById(R.id.editTextStudentName)
        editTextId = view.findViewById(R.id.editTextStudentId)

        // Nút thêm sinh viên
        view.findViewById<Button>(R.id.buttonAddStudent).setOnClickListener {
            addStudent()
        }
    }

    private fun addStudent() {
        val studentName = editTextName.text.toString().trim()
        val studentId = editTextId.text.toString().trim()

        if (studentName.isEmpty()) {
            editTextName.error = "Tên sinh viên không được để trống"
            return
        }

        if (studentId.isEmpty()) {
            editTextId.error = "Mã sinh viên không được để trống"
            return
        }

        val newStudent = StudentModel(studentName, studentId)

        // Thêm sinh viên vào danh sách
        val mainActivity = activity as? MainActivity
        mainActivity?.students?.add(newStudent)

        findNavController().navigateUp()
    }
}
