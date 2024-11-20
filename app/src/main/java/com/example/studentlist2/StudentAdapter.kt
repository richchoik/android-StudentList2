import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.studentlist2.R
import com.example.studentlist2.StudentModel

class StudentAdapter(
    private val context: Context,
    private val students: MutableList<StudentModel>,
    private val onEditClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = students.size

    override fun getItem(position: Int): Any = students[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.layout_student_item, parent, false)

        val textStudentName: TextView = view.findViewById(R.id.text_student_name)
        val textStudentId: TextView = view.findViewById(R.id.text_student_id)
        val imageEdit: ImageView = view.findViewById(R.id.image_edit)
        val imageRemove: ImageView = view.findViewById(R.id.image_remove)

        val student = students[position]
        textStudentName.text = student.studentName
        textStudentId.text = student.studentId

        imageEdit.setOnClickListener {
            onEditClick(position)
        }

        imageRemove.setOnClickListener {
            onDeleteClick(position)
        }

        return view
    }
}
