package com.example.student_manager.fragments

import StudentModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.student_manager.MainActivity
import com.example.student_manager.R
import androidx.navigation.fragment.findNavController

class EditStudentFragment : Fragment() {
    private lateinit var editTextName: EditText
    private lateinit var editTextId: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Lấy sinh viên từ arguments
        val student = requireArguments().getParcelable<StudentModel>("student")!!

        editTextName = view.findViewById(R.id.editTextStudentName)
        editTextId = view.findViewById(R.id.editTextStudentId)

        editTextName.setText(student.studentName)
        editTextId.setText(student.studentId)

        view.findViewById<Button>(R.id.buttonUpdateStudent).setOnClickListener {
            val updatedStudent = StudentModel(
                editTextName.text.toString(),
                editTextId.text.toString()
            )

            // Cập nhật sinh viên
            (activity as MainActivity).updateStudent(
                student.studentName,
                student.studentId,
                updatedStudent
            )

            findNavController().navigateUp()
        }
    }
}