# Jhipster là gì?
Jhipster là công cụ giúp generate Spring Boot +Angular/React Web application
# Cài đặt Jhipster
1. Cài đặt JDK version 20
    [Download JDK version 20](https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html)

    Thêm biến môi trường JAVA_HOME
    ![image](https://github.com/HuyHKr/Quan-Ly-Nhan-Khau/assets/148759236/be337209-7ea2-4537-b584-0fc731972edd)
3. Cài đặt Nodejs version 20.10.0
   [Download Nodejs version 20.10.0](https://nodejs.org/en/blog/release/v20.10.0)
4. Cài đặt Git
   [Download Git](https://git-scm.com/downloads)
5. Cài đặt generator-jhipster
   `npm i -g generator-jhipster`

# Demo Tạo 1 Project bằng Jhipster sử dụng PostgreSql
1. Tạo Project QuanLyNhanKhau
   ```
   mkdir QuanLyNhanKhau
   cd QuanLyNhanKhau
   jhipster
   ```
![image](https://github.com/HuyHKr/Quan-Ly-Nhan-Khau/assets/148759236/61d39558-5558-49a9-ab26-0af28c797579)

   
3. Tạo các entity

    Định nghĩa tập thực thể trong [JDL Studio](https://start.jhipster.tech/jdl-studio/)
    ![image](https://github.com/HuyHKr/Quan-Ly-Nhan-Khau/assets/148759236/e367a53f-6510-42d4-913a-403cec5a5c1d)
    Tải về và copy vào source code

    Chạy lệnh `jhipster jdl jhipster-jdl.jdl` (jhipster-jdl.jdl là file vừa tải)
    ![image](https://github.com/HuyHKr/Quan-Ly-Nhan-Khau/assets/148759236/970db685-c2ac-4ca0-93f2-b56bad168511)

    Có conflict, nhấn **a** và **Enter** để ghi đè tất cả file conflict
    ![image](https://github.com/HuyHKr/Quan-Ly-Nhan-Khau/assets/148759236/05126218-1729-4ed4-ad49-3a6a7645ee65)

5. Tạo Database và connect source code với Database

   [Tải PostgreSql](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)

   Tạo database có tên quanlynhankhau

   Config source code tại  `src\main\resources\config\ application-dev.yml `
        ![image](https://github.com/HuyHKr/Quan-Ly-Nhan-Khau/assets/148759236/a781ccbe-5c5c-4d18-a48c-4e63f2312208)


6. Khởi chạy Project
   
   Chạy lệnh `mvnw` để khởi động 
   ![image](https://github.com/HuyHKr/Quan-Ly-Nhan-Khau/assets/148759236/16d4d2c5-7643-4893-8995-21ecef09f523)
  
   Thêm `import static org.mockito.Mockito.when;` vào file UserServiceIT.  khi có lỗi sau
   ![image](https://github.com/HuyHKr/Quan-Ly-Nhan-Khau/assets/148759236/fd7a04a7-605f-41c7-ad7d-fa4becf09ac9)
   Thành công sẽ Hiện dòng text sau
   ![image](https://github.com/HuyHKr/Quan-Ly-Nhan-Khau/assets/148759236/2f279597-5246-4e28-a270-39616d4b44f0)
   Chạy `npm start` để khởi động FrontEnd

   Hình minh họa Web đã tạo
   ![image](https://github.com/HuyHKr/Quan-Ly-Nhan-Khau/assets/148759236/5614664a-5649-4969-81da-d17bd6a13723)
   ![image](https://github.com/HuyHKr/Quan-Ly-Nhan-Khau/assets/148759236/48567b62-85d7-4e87-8115-ba3ba350cd64)
   ![image](https://github.com/HuyHKr/Quan-Ly-Nhan-Khau/assets/148759236/e9159862-33bd-4080-a3e8-0e53c61918c5)

# Ưu điểm
Một trong những ưu điểm quan trọng của JHipster là khả năng hỗ trợ Bootstrap, cho phép xây dựng giao diện người dùng một cách dễ dàng và linh hoạt. Việc này giúp tối ưu hóa trải nghiệm người dùng và đồng thời giảm độ phức tạp trong việc phát triển giao diện.

JHipster cũng ấn tượng với khả năng đa ngôn ngữ, cho phép ứng dụng được phát triển và triển khai trên nhiều thị trường và cộng đồng người dùng khác nhau. Điều này nâng cao khả năng tiếp cận và sử dụng ứng dụng trên phạm vi toàn cầu.

Khả năng tương thích với nhiều hệ cơ sở dữ liệu khác nhau là một điểm mạnh khác của JHipster. Người phát triển có thể lựa chọn sử dụng cơ sở dữ liệu phù hợp với yêu cầu cụ thể của dự án, giúp tối ưu hóa hiệu suất và quản lý dữ liệu một cách linh hoạt.

Việc chỉ cần định nghĩa tập thực thể và sau đó JHipster tự động sinh ra một dự án đầy đủ từ API đến giao diện làm giảm đáng kể thời gian và công sức phát triển. Chức năng tự động tạo đăng nhập cũng là một ưu điểm lớn, giúp người phát triển tập trung vào các tính năng quan trọng hơn của ứng dụng.

Cuối cùng, cấu trúc code được tổ chức vào các package rõ ràng, giúp dễ dàng quản lý và bảo trì mã nguồn. Điều này hỗ trợ tính mô-đun hóa và mở rộng của ứng dụng, làm cho quá trình phát triển trở nên linh hoạt và dễ dàng mở rộng theo thời gian.

# Nhược điểm

Không thể custom cơ sở dữ liệu được, Lập trình viên chỉ có thể định nghĩa  tập các thực thể và mối quan hệ giữa chúng, còn việc sinh cơ sở dữ liệu là do jhipster quyết định. Ví dụ như muốn tạo ra thực thể nhân khẩu với khóa chính là mã nhân khẩu sẽ rất khó vì jhipster mặc định tạo khóa chính là id. 

Mặc định, khi chỉnh sửa file **Jdl**  và recompile thì chỉ generate entity chứ không cập nhật trên database

Do là công cụ tạo code tự động nên mỗi phiên bản của jhipster chỉ tương thích với một số phiên bản JDK và Nodejs nhất định giống như đã cài đặt ở trên. Nếu sai phiên bản có thể gây ra lỗi thư viện, nếu là người mới bắt đầu thì rất khó để sửa lỗi và rất mất thời gian. Kể cả có đúng phiên bản thì code sinh ra vẫn có lỗi như phía trên đã cài đặt

Ví dụ như với bản jhipster version 8 chỉ tương thích với jdk 17 đến 21. Nhưng dùng jdk 21 vẫn báo lỗi này. Chuyển sang JDK 20 thì được

![image](https://github.com/HuyHKr/Quan-Ly-Nhan-Khau/assets/148759236/33c469a1-e670-4455-9bf3-1610a4676095)

Lỗi webpack 
![image](https://github.com/HuyHKr/Quan-Ly-Nhan-Khau/assets/148759236/137daefb-9b68-477c-9003-99ba0d3165ab)









   
   

    
