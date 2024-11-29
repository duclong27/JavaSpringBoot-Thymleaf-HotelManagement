

function updateCheckOutMinDate() {
    // Lấy giá trị ngày check-in
    var checkInDate = document.getElementById('checkInDate').value;

    // Lấy ngày hôm nay
    var today = new Date();
    today.setHours(0, 0, 0, 0); // Đặt lại giờ, phút, giây, mili giây để so sánh chỉ ngày

    // Nếu ngày check-in hợp lệ và là hôm nay hoặc muộn hơn
    if (checkInDate) {
        var checkIn = new Date(checkInDate);

        // Kiểm tra nếu ngày check-in trước hôm nay
        if (checkIn < today) {
            alert("Ngày check-in phải là hôm nay hoặc muộn hơn.");
            document.getElementById('checkInDate').value = today.toISOString().split('T')[0]; // Cập nhật ngày check-in thành hôm nay
            return; // Dừng lại nếu ngày check-in không hợp lệ
        }

        // Tạo ngày check-out mới là ngày sau check-in một ngày
        checkIn.setDate(checkIn.getDate() + 1); // Thêm 1 ngày

        // Định dạng lại ngày check-out mới theo kiểu yyyy-mm-dd
        var year = checkIn.getFullYear();
        var month = (checkIn.getMonth() + 1).toString().padStart(2, '0');
        var day = checkIn.getDate().toString().padStart(2, '0');
        var minCheckOutDate = `${year}-${month}-${day}`;

        // Cập nhật thuộc tính 'min' cho ngày check-out
        document.getElementById('checkOutDate').setAttribute('min', minCheckOutDate);

        // Nếu ngày check-out nhỏ hơn ngày check-in (dù đã có min), đặt lại ngày check-out thành ngày tiếp theo
        var checkOutDate = document.getElementById('checkOutDate').value;
        if (checkOutDate && new Date(checkOutDate) < new Date(minCheckOutDate)) {
            document.getElementById('checkOutDate').value = minCheckOutDate;
        }
    }
}


