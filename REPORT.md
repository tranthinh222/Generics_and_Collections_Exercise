# Báo cáo bài tập seminar: Hệ thống quản lý thư viện

**MSSV**: 23120168

**Họ và tên**: Trần Quốc Thịnh

## I. Đánh giá mức độ hoàn thành:

| STT | Tên tính năng                             | Mức độ hoàn thành |
| :-: | ----------------------------------------- | :---------------: |
|  1  | Tạo tài khoản thủ thư                     |       100%        |
|  2  | Đăng nhập, đăng xuất                      |       100%        |
|  3  | Xem danh sách độc giả trong thư viện      |       100%        |
|  4  | Thêm độc giả                              |       100%        |
|  5  | Chỉnh sửa thông tin một độc giả           |       100%        |
|  6  | Xóa thông tin một độc giả                 |       100%        |
|  7  | Tìm kiếm độc giả theo CMND/CCCD và họ tên |       100%        |
|  8  | Xem danh sách các sách trong thư viện     |       100%        |
|  9  | Thêm sách                                 |       100%        |
| 10  | Chỉnh sửa thông tin một quyển sách        |       100%        |
| 11  | Xóa thông tin sách                        |       100%        |
| 12  | Tìm kiếm sách theo ISBN và tên sách       |       100%        |
| 13  | Lập phiếu mượn sách                       |       100%        |
| 14  | Lập phiếu trả sách                        |       100%        |
| 15  | Thống kê các số liệu cơ bản               |       100%        |

---

\newpage

### Git Commits cho từng tính năng

**Tính năng 1: Tạo tài khoản thủ thư**

![](images/commit-1.png)

**Tính năng 2: Đăng nhập, đăng xuất**

![](images/commit-2.png)
![](images/commit-3.png)

**Tính năng 3: Xem danh sách độc giả trong thư viện**

![](images/commit-4.png)
![](images/commit-5.png)


**Tính năng 4: Thêm độc giả**

![](images/commit-6.png)

\newpage

**Tính năng 5: Chỉnh sửa thông tin một độc giả**

![](images/commit-7.png)

**Tính năng 6: Xóa thông tin một độc giả**

![](images/commit-8.png)


**Tính năng 7: Tìm kiếm độc giả theo CMND/CCCD và họ tên**

![](images/commit-9.png)

**Tính năng 8: Xem danh sách các sách trong thư viện**

![](images/commit-10.png)

\newpage

**Tính năng 9: Thêm sách**

![](images/commit-11.png)


**Tính năng 10: Chỉnh sửa thông tin một quyển sách**

![](images/commit-12.png)

**Tính năng 11: Xóa thông tin sách**

![](images/commit-13.png)

**Tính năng 12: Tìm kiếm sách theo ISBN và tên sách**

![](images/commit-14.png)

\newpage

**Tính năng 13: Lập phiếu mượn sách**

![](images/commit-15.png)

**Tính năng 14: Lập phiếu trả sách**

![](images/commit-16.png)

**Tính năng 15: Thống kê các số liệu cơ bản**

![](images/commit-17.png)

## II. Mức độ sử dụng AI:

**Mức độ sử dụng AI trong bài tập:** **[10%]**

**Báo cáo sử dụng mẫu B để khai báo việc sử dụng AI trong bài tập**

- **ChatGPT**. GPT-5.3, OpenAI, chat.openai.com, truy cập lúc 09:13 ngày 8 tháng 4 năm 2026, prompts:
  - "tôi đang làm java swing, tôi muốn các button theo thứ tự từ trái sang phải thì làm bằng cách nào"
  - "giúp tôi visualize từng loại Layout để tôi hiểu hơn"

  Mục đích: tìm layout phù hợp cho thanh điều hướng (Login, Readers, Books, ...); AI đề xuất 3 layouts: FlowLayout, BoxLayout, GridLayout với mô tả hình ảnh cụ thể; sinh viên chọn FlowLayout dựa trên gợi ý này và triển khai cho giao diện thanh điều hướng.

![alt text](images/1.png)
![alt text](images/2.png)
![alt text](images/3.png)
![alt text](images/4.png)
![alt text](images/5.png)
![alt text](images/6.png)
![alt text](images/7.png)
![alt text](images/8.png)
![alt text](images/9.png)
![alt text](images/10.png)

- **ChatGPT**. GPT-5.3, OpenAI, chat.openai.com, truy cập lúc 14:25 ngày 9 tháng 4 năm 2026, prompts:
  - "tôi muốn ở một thời điểm, frame chỉ có thể mở 1 Panel"
  - "cho tôi xin code mẫu sử dụng CardLayout"

  Mục đích: tìm cách quản lý hiển thị của nhiều panel trong cùng một frame; AI đề xuất CardLayout và cung cấp code mẫu; sinh viên chạy thử code mẫu và điều chỉnh cho phù hợp với việc quản lý các panel trên thanh điều hướng.

![alt text](images/11.png)
![alt text](images/12.png)
![alt text](images/13.png)
![alt text](images/14.png)
![alt text](images/15.png)
![alt text](images/16.png)
![alt text](images/17.png)
![alt text](images/18.png)
![alt text](images/19.png)

- **ChatGPT**. GPT-5.3, OpenAI, chat.openai.com, truy cập lúc 20:24 ngày 13 tháng 4 năm 2026, prompt: "tôi bị một vấn đề là khi tôi gõ R041 ở ô mã độc giả, khi click vào field tiếp theo, thông tin R041 tự động nhảy xuống field khác, hoặc khi tôi gõ nó gạch chân R041 nó tự động nhảy sang field khác nếu click vào field đó hoặc click ra ngoài nó tự xóa R041 luôn"; mục đích: xác định nguyên nhân lỗi nhập liệu và xử lý sự kiện; AI gợi ý nguyên nhân liên quan đến xử lý sự kiện input và focus (DocumentListener/FocusListener); sinh viên đã kiểm tra lại code xử lý sự kiện và kiểm tra hoạt động của các field sau khi sửa.

![alt text](images/20.png)
![alt text](images/21.png)
![alt text](images/22.png)
![alt text](images/23.png)

- ChatGPT. GPT-5.3, OpenAI, chat.openai.com, truy cập lúc 21:40 ngày 13 tháng 4 năm 2026, prompt: "tôi vừa đọc một bài blog: https://viblo.asia/... và tôi gặp trường hợp y như vậy, hãy giúp tôi disable ime này"; mục đích: tìm cách xử lý lỗi nhập liệu liên quan đến IME trong Java Swing; AI đề xuất 3 phương án như vô hiệu hóa input method (enableInputMethods(false)), sử dụng InputMethodListener và kiểm soát input bằng KeyListener; sinh viên đã lựa chọn phương pháp vô hiệu hóa input method cho JTextField, áp dụng vào chương trình, chạy thử và xác nhận lỗi không còn xảy ra.

![alt text](images/24.png)
![alt text](images/25.png)
![alt text](images/26.png)
![alt text](images/27.png)
