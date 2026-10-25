Mô tả Nâng Cao

NC2: Dòng có giá trên 45.000 đ thì nền đổi màu nhạt.
Trong getView của MonAnAdapter, sau khi gán dữ liệu, kiểm tra giá món: nếu lớn hơn 45.000 đ thì gọi setBackgroundColor đổi nền dòng sang vàng nhạt (#FFF3CD), ngược lại đặt về Color.TRANSPARENT. 
Nhánh else là bắt buộc vì ListView tái sử dụng dòng cũ (convertView), nếu không đặt lại màu thì dòng cuộn lên sẽ dính màu của dòng trước, làm sai màu.

NC4: Hiện tổng số món và tổng giá trị thực đơn.
Thêm một TextView (tvThongKe) phía trên ListView và hàm capNhatThongKe(). 
Hàm này duyệt danhSach, cộng getGia() của từng món, rồi hiển thị “Tổng: N món ; X đ” bằng chuỗi trong strings.xml và MonAn.dinhDangGia. 
Hàm được gọi một lần khi mở app và sau mỗi lần thêm hoặc xóa món, nên số liệu luôn khớp với danh sách.
