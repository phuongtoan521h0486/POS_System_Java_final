Enviroment Requirement
- Java 11
- IntelliJ IDEA
- XAMPP
---------------------------------------------------------------------------------------------------------
Extract the file POS_System_Java_Final.zip in folder Source code
Open Intellij IDEA and open project with the folder: POS_System_Java_Final in folder source code.

You can clone the POS_System_Java_Final in https://github.com/phuongtoan521h0486/POS_System_Java_final

Run Xampp and start Service Apache and MySql.

Access the http://localhost/phpmyadmin/ by click at Admin button.
Import the database by pos_db.sql in Database folder.

!!! If you fail when import database, please try this !!!
Changing php.ini at C:\xampp\php\php.ini

max_execution_time = 600
max_input_time = 600
memory_limit = 1024M
post_max_size = 1024M

Changing my.ini at C:\xampp\mysql\bin\my.ini

max_allowed_packet = 1024M

After changing save all and try to import again
---------------------------------------------------------------------------------------------------------
Click at play button in Intellij IDEA to run app

Access to the http://localhost:8888/

Admin account:
	Username: admin
	password: 123456

For more infomation you can read Readme.md file