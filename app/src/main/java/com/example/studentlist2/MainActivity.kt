package com.example.studentlist2

import StudentAdapter
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : ComponentActivity() {
    private val students = mutableListOf(
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
    private lateinit var studentAdapter: StudentAdapter
    private var recentlyDeletedStudent: StudentModel? = null
    private var recentlyDeletedPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentAdapter = StudentAdapter(this, students, ::showEditStudentDialog, ::showDeleteConfirmationDialog)
        val listView = findViewById<ListView>(R.id.list_view_students)
        listView.adapter = studentAdapter

        val btnAddNew = findViewById<Button>(R.id.btn_add_new)
        btnAddNew.setOnClickListener {
            showAddStudentDialog()
        }
    }

    private fun showAddStudentDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_edit_student, null)
        val inputName = dialogView.findViewById<android.widget.EditText>(R.id.input_student_name)
        val inputId = dialogView.findViewById<android.widget.EditText>(R.id.input_student_id)

        MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle("Thêm Sinh Viên")
            .setView(dialogView)
            .setPositiveButton("Thêm") { _, _ ->
                val name = inputName.text.toString()
                val id = inputId.text.toString()
                if (name.isNotBlank() && id.isNotBlank()) {
                    students.add(StudentModel(name, id))
                    studentAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "Thông tin không hợp lệ", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun showEditStudentDialog(position: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_edit_student, null)
        val inputName = dialogView.findViewById<android.widget.EditText>(R.id.input_student_name)
        val inputId = dialogView.findViewById<android.widget.EditText>(R.id.input_student_id)

        val student = students[position]
        inputName.setText(student.studentName)
        inputId.setText(student.studentId)

        MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle("Chỉnh Sửa Sinh Viên")
            .setView(dialogView)
            .setPositiveButton("Cập Nhật") { _, _ ->
                val name = inputName.text.toString()
                val id = inputId.text.toString()
                if (name.isNotBlank() && id.isNotBlank()) {
                    students[position] = StudentModel(name, id)
                    studentAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "Thông tin không hợp lệ", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun showDeleteConfirmationDialog(position: Int) {
        MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle("Xóa Sinh Viên")
            .setMessage("Bạn có chắc chắn muốn xóa sinh viên này không?")
            .setPositiveButton("Xóa") { _, _ ->
                recentlyDeletedStudent = students[position]
                recentlyDeletedPosition = position
                students.removeAt(position)
                studentAdapter.notifyDataSetChanged()
                showUndoSnackbar()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun showUndoSnackbar() {
        val rootView = findViewById<android.view.View>(android.R.id.content)
        Snackbar.make(rootView, "Đã xóa sinh viên", Snackbar.LENGTH_LONG)
            .setAction("Hoàn Tác") {
                recentlyDeletedStudent?.let {
                    students.add(recentlyDeletedPosition, it)
                    studentAdapter.notifyDataSetChanged()
                }
            }
            .show()
    }
}


