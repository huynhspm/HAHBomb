# HAHBomb
### Lớp : 2122I_INT2204_2 - Nhóm : N1 - Trịnh Ngọc Huỳnh 20020054 - Phạm Huy Hoàng 20020053 - Nguyễn Thế Anh 20020096
***
## Giới thiệu HAH BOMB
### 1. Các đối tượng:
- Wall: ô không được đi vào
- Box: ô có thể phá đươc bằng bomb để nhặt item
- Item: có 4 loại item
      +) Heart: + 1 mạng
      +) Flame: + 1 độ rộng của explode do bomb gây ra (Max = 4)
      +) Bomb:  + 1 số lượng quả bomb được đặt tối đa liên tiếp (sau khi 1 quả bomb do người nào đặt nổ thì số lượng bomb lại hồi)
      +) Speed: + 1 tốc độ (Max nhặt 4 giày)
- Background: nền của các ô
- Bomb: đối tượng bomb do character tạo ra
- Character: có 2 loại
      +) Enemy: kẻ địch, chức năng thay đổi qua các level
      +) Player: người chơi di chuyển và đặt bomb trong phần 2 - Cách chơi
- Explode: vụ nổ sao bị bomb nổ tạo ra
- Portal: cổng dịch chuyển để qua level sao khi giết hết enemy
### 2. Cách chơi
- người chơi di chuyển bằng WASD, hoặc 4 phím hướng (chi tiết xem tại HOW TO PLAY trong game)
- đặt bomb bằng SPACE / ENTER
- giết hết enemy để portal hiện ra và đi vào để next level
### 3. Các level
- level 1: enemy đi random, sẽ choáng người chơi 2s nếu va chạm, không ăn itemHeart chỉ dẫm mất
- level 2: enemy đi random và đi xuyên box, đặt bomb giết player, không ăn itemHeart chỉ dẫm mất
- level 3: enemy đi thông minh đuổi theo player đặt bomb phá box và giết player, kĩ năng né bomb cấp 1, ăn item như người chơi
- level 4: enemy đi thông minh đuổi theo player đặt bomb phá box và giết player, kĩ năng né bomb cấp 2, ăn item như người chơi
- level 5: enemy đi thông minh đuổi theo player đặt bomb phá box và giết player, kĩ năng né bomb cấp 3, ăn item như người chơi
### 4. Cài đặt và chơi
- Tải jdk17 (bản jdk17.exe cho window) tại đây https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
- Cài đặt java vào môi trường máy (thường thì chạy bản exe sẽ set up sẵn java cho máy).
- Chỉ cần download folder PlayGame sau đó mở game.jar để chơi
***
## Update:
### Update 1: Tạo riêng hàm create cho từng objects, tạo class enum riêng để chứa toàn bộ enum
### Update 2: Tạo cây thừa kế cho Item để thuận tiện phát triển về sau khi thêm Item
### Update 3: Tạo cây thừa kế cho EnemyController và Enemy để thuận tiện phát triển về sau khi thêm enemy mới
## Error:
### lỗi không đặt lại bomb
### boss level 5 tự sát
### map đi liên thông hơi khó
