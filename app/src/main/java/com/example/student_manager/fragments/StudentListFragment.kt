package com.example.student_manager.fragments

import StudentAdapter
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.student_manager.MainActivity
import com.example.student_manager.R
import androidx.navigation.fragment.findNavController

class StudentListFragment : Fragment() {
    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView = view.findViewById(R.id.listViewStudents)
        val adapter = StudentAdapter(requireContext(), (activity as MainActivity).students)
        listView.adapter = adapter

        // Đăng ký context menu
        registerForContextMenu(listView)

        // Sự kiện click để chỉnh sửa
        listView.setOnItemClickListener { _, _, position, _ ->
            val student = (activity as MainActivity).students[position]

            // Điều hướng đến EditStudentFragment với dữ liệu sinh viên
            findNavController().navigate(
                R.id.action_studentListFragment_to_editStudentFragment,
                bundleOf("student" to student)
            )
        }
    }

    // Tạo context menu
    override fun onCreateContextMenu(
        menu: ContextMenu,
        view: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, view, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.context_menu, menu)
    }

    // Xử lý sự kiện chọn item trong context menu
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val mainActivity = activity as MainActivity
        val students = mainActivity.students

        return when (item.itemId) {
            R.id.edit_student -> {
                val student = students[info.position]
                findNavController().navigate(
                    R.id.action_studentListFragment_to_editStudentFragment,
                    bundleOf("student" to student)
                )
                true
            }
            R.id.remove_student -> {
                students.removeAt(info.position)
                (listView.adapter as StudentAdapter).notifyDataSetChanged()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}