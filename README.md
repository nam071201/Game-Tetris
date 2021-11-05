# Tetris
Đây là dự án mà tôi mã hóa là môn cuối cùng của AP Computer Science vào năm 2021. Đó là một trò chơi Tetris đơn giản trong Java (thư mục Tetris-Client), với tùy chọn 1 người chơi và 2 người chơi. Để cho phép chơi 2 người chơi, bạn cần có máy chủ Tetris Node.js mà tôi đã tạo cùng với nó (thư mục Tetris-Server). 

Đây là _no_ có nghĩa là được cho là ở bất kỳ cấp độ sản xuất nào, chỉ có nghĩa là một trò chơi đơn giản để lưu trữ trên mạng cục bộ với bạn bè. Nó không có bảo mật và IP được mã hóa cứng thành mã nguồn. 

## Gameplay
### Controls
* **Left Arrow** — Move left
* **Right Arrow** — Move right
* **Up Arrow** — Rotate
* **Down Arrow** — Move down
* **Space Bar** — Move all the way down and get next piece
* **Escape** — Pause game

### Multiplayer functionality
Hai người chơi đang chơi Tetris cùng một lúc. Mỗi lần phá vỡ một hàng, đối thủ của bạn sẽ nhận được một "hàng cũ" hoặc một hàng khối màu xám ở cuối màn hình của họ, giúp họ có ít không gian hơn để chơi. Nếu bạn ngắt một hàng và bạn đã có các hàng cũ ở cuối màn hình, thì mỗi hàng bạn ngắt trước tiên sẽ xóa các hàng cũ đó trước khi chúng được đưa cho đối thủ. Người đầu tiên lên đỉnh thua cuộc. 

## Installation
### Set Up Server
Để thiết lập máy chủ Node.js, hãy tìm môi trường yêu thích của bạn và chạy lệnh này trong thư mục `Tetris-Server`: 
```bash
npm install
```

Bây giờ bạn đã sẵn sàng để chạy máy chủ: 
```bash
node index.js
```

Nó sẽ cho bạn biết cổng mà nó đang chạy trên bảng điều khiển, đó là cổng `4444`. Ghi lại IP hoặc tên miền của máy chủ lưu trữ, vì bạn sẽ cần thêm tên miền này vào Ứng dụng khách Tetris trước khi biên dịch mã nguồn của nó. Nếu bạn muốn truy cập nó ngoài phạm vi mạng cục bộ của mình, hãy đảm bảo rằng bạn đã chuyển tiếp mạng của mình. 

### Set Up Client
Trong thư mục `Tetris-Client`, tìm dòng 19 trong tệp` src / InternetUtilities.java`. Thay đổi văn bản từ `YOUR-IP-HERE.COM` thành IP hoặc tên miền của bạn. 

Bây giờ bạn đã sẵn sàng để biên dịch và chạy Ứng dụng khách Tetris của mình. 

## Images
### Title screen
![Title screen](https://i.imgur.com/7m7Zz7T.png)
### One player screen
![One player screen](https://i.imgur.com/Zp9XdO2.png)
### Instructions
![Instructions](https://i.imgur.com/CZ9L6qe.png)
### Two player screen
![Two player screen](https://i.imgur.com/SmOGDxJ.png)
