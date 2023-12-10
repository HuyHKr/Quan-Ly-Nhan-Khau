1. Jhipster là gì?
   Jhipster là công cụ giúp generate Spring Boot +Angular/React Web application
2. Cài đặt Jhipster
3. Cài đặt JDK version 20
   Java Archive Downloads - Java SE 20 (oracle.com)
   Thêm biến môi trường JAVA_HOME

2.Cài đặt Nodejs version 20.10.0
Download | Node.js (nodejs.org)
3.Cài đặt git

Git - Downloads (git-scm.com)
3.Cài đặt generator-jhipster
Npm i -g generator-jhipster 3. Tạo Project bằng Jhipster sử dụng PostgreSql
Cài đặt PostgreSql
Community DL Page (enterprisedb.com)
Mở command prompt chạy lệnh sau
Mkdir MyApp
Cd MyApp
Jhipster
Trả lời các câu hỏi

Vào JDL-Studio (jhipster.tech) để định nghĩa các thực thể và tải file jdl về

Copy file vào folder đã tạo ở trên
Chạy lệnh jhipster jdl jhipster-jdl.jdl ( với jhipster-jdl.jdl là file vừa tải)

Nếu hiện dòng lệnh sau thì ấn a và enter để override all

Tiếp theo, cần config Database
vào file src\main\resources\config\ application-dev.yml tìm đến datasource, sửa username, password phù hợp

Tạo database có tên quanlynhankhau bằng psql (với quanlynhankhau là tên project)
Mở lại project với cmd chạy lệnh mvnw

Sinh ra lỗi sau, cần vào đúng file UserServiceIT.java để sửa
Thêm dòng này vào đầu file:
import static org.mockito.Mockito.when;

Nếu thành công sẽ hiện dòng text như sau

Chạy lệnh npm start để khởi động FrontEnd
Hình ảnh minh họa project được sinh ra

4. Ưu điểm
   Một trong những ưu điểm quan trọng của JHipster là khả năng hỗ trợ Bootstrap, cho phép xây dựng giao diện người dùng một cách dễ dàng và linh hoạt. Việc này giúp tối ưu hóa trải nghiệm người dùng và đồng thời giảm độ phức tạp trong việc phát triển giao diện.

JHipster cũng ấn tượng với khả năng đa ngôn ngữ, cho phép ứng dụng được phát triển và triển khai trên nhiều thị trường và cộng đồng người dùng khác nhau. Điều này nâng cao khả năng tiếp cận và sử dụng ứng dụng trên phạm vi toàn cầu.

Khả năng tương thích với nhiều hệ cơ sở dữ liệu khác nhau là một điểm mạnh khác của JHipster. Người phát triển có thể lựa chọn sử dụng cơ sở dữ liệu phù hợp với yêu cầu cụ thể của dự án, giúp tối ưu hóa hiệu suất và quản lý dữ liệu một cách linh hoạt.

Việc chỉ cần định nghĩa tập thực thể và sau đó JHipster tự động sinh ra một dự án đầy đủ từ API đến giao diện làm giảm đáng kể thời gian và công sức phát triển. Chức năng tự động tạo đăng nhập cũng là một ưu điểm lớn, giúp người phát triển tập trung vào các tính năng quan trọng hơn của ứng dụng.

Cuối cùng, cấu trúc code được tổ chức vào các package rõ ràng, giúp dễ dàng quản lý và bảo trì mã nguồn. Điều này hỗ trợ tính mô-đun hóa và mở rộng của ứng dụng, làm cho quá trình phát triển trở nên linh hoạt và dễ dàng mở rộng theo thời gian. 5. Nhược điểm
Không thể custom cơ sở dữ liệu được, Lập trình viên chỉ có thể định nghĩa tập các thực thể và mối quan hệ giữa chúng, còn việc sinh cơ sở dữ liệu là do jhipster quyết định. Ví dụ như muốn tạo ra thực thể nhân khẩu với khóa chính là mã nhân khẩu sẽ rất khó vì jhipster mặc định tạo khóa chính là id.
Do là công cụ tạo code tự động nên mỗi phiên bản của jhipster chỉ tương thích với một số phiên bản JDK và Nodejs nhất định giống như đã cài đặt ở trên. Nếu sai phiên bản có thể gây ra lỗi thư viện, nếu là người mới bắt đầu thì rất khó để sửa lỗi.  
Ví dụ như với bản jhipster version 8 chỉ tương thích với jdk 17 đến 21. Nhưng dùng jdk 21 vẫn báo lỗi này. Chuyển sang JDK 20 thì được

Lỗi webpack
