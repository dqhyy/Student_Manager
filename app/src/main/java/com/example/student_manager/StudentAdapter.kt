import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.student_manager.R

class StudentAdapter(
    private val context: Context,
    private val students: MutableList<StudentModel>
) : BaseAdapter() {

    override fun getCount(): Int = students.size

    override fun getItem(position: Int): Any = students[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.item_student, parent, false
        )

        val student = students[position]
        val nameTextView = view.findViewById<TextView>(R.id.studentName)
        val idTextView = view.findViewById<TextView>(R.id.studentId)

        nameTextView.text = student.studentName
        idTextView.text = student.studentId

        return view
    }
}
