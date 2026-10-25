package vn.edu.vhu.ltdd.a9listview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<MonAn> danhSach = new ArrayList<>();
    private MonAnAdapter adapter;
    private ListView lvMonAn;
    private TextView tvTrong, tvThongKe;
    private EditText edtTen, edtGia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets s = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(s.left, s.top, s.right, s.bottom);
            return insets;
        });

        lvMonAn = findViewById(R.id.lvMonAn);
        tvTrong = findViewById(R.id.tvTrong);
        tvThongKe = findViewById(R.id.tvThongKe);
        edtTen = findViewById(R.id.edtTen);
        edtGia = findViewById(R.id.edtGia);
        Button btnThem = findViewById(R.id.btnThem);

        taoDuLieuMau();
        adapter = new MonAnAdapter(this, danhSach);
        lvMonAn.setAdapter(adapter);
        lvMonAn.setEmptyView(tvTrong);
        capNhatThongKe();

        // Chạm: hiện tên món
        lvMonAn.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(this,
                        getString(R.string.chon_mon, danhSach.get(position).getTen()),
                        Toast.LENGTH_SHORT).show());

        // Giữ lâu: xóa món
        lvMonAn.setOnItemLongClickListener((parent, view, position, id) -> {
            MonAn daXoa = danhSach.remove(position);
            adapter.notifyDataSetChanged();
            capNhatThongKe();
            Toast.makeText(this, getString(R.string.da_xoa, daXoa.getTen()),
                    Toast.LENGTH_SHORT).show();
            return true;
        });

        btnThem.setOnClickListener(v -> themMon());
    }

    private void taoDuLieuMau() {
        danhSach.add(new MonAn("Phở bò tái", "Nước dùng hầm xương 12 giờ", 45000, R.drawable.mon1));
        danhSach.add(new MonAn("Bún chả Hà Nội", "Thịt nướng than hoa, nước chấm chua ngọt", 50000, R.drawable.mon2));
        danhSach.add(new MonAn("Cơm tấm sườn", "Sườn nướng, bì, chả trứng", 40000, R.drawable.mon3));
        danhSach.add(new MonAn("Bánh mì thịt nguội", "Giòn rụm, pate béo ngậy", 25000, R.drawable.mon4));
        danhSach.add(new MonAn("Trà đào cam sả", "Mát lạnh, thơm sả", 30000, R.drawable.mon5));
        // TẠM THỜI: thêm 20 món để danh sách đủ dài mà cuộn (xóa sau khi chụp ảnh Logcat)
        for (int i = 6; i <= 25; i++) {
            danhSach.add(new MonAn("Món thử số " + i, "Món để thử cuộn danh sách", 10000 + i * 1000, R.drawable.mon1));
        }
    }

    private void themMon() {
        String ten = edtTen.getText().toString().trim();
        String giaStr = edtGia.getText().toString().trim();

        if (ten.isEmpty()) {
            edtTen.setError(getString(R.string.err_ten));
            return;
        }
        int gia;
        try {
            gia = Integer.parseInt(giaStr);
        } catch (NumberFormatException e) {
            edtGia.setError(getString(R.string.err_gia));
            return;
        }
        if (gia <= 0) {
            edtGia.setError(getString(R.string.err_gia_duong));
            return;
        }

        danhSach.add(0, new MonAn(ten, getString(R.string.moi_them), gia, R.drawable.mon1));
        adapter.notifyDataSetChanged();
        capNhatThongKe();
        lvMonAn.smoothScrollToPosition(0);
        edtTen.setText("");
        edtGia.setText("");
    }

    /** NC4: cập nhật tổng số món và tổng giá trị thực đơn. */
    private void capNhatThongKe() {
        int tong = 0;
        for (MonAn m : danhSach) tong += m.getGia();
        tvThongKe.setText(getString(R.string.thong_ke, danhSach.size(), MonAn.dinhDangGia(tong)));
    }
}