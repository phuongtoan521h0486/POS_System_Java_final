
# POS System Java website

This project is the final assignment for the Java Technology course.

## Features

- Account management
- Product Catalog Management (Admin only)
- Customer Management
- Transaction Processing
- Reporting and Analytics

For detail: [Specification Requirements](https://github.com/phuongtoan521h0486/POS_System_Java_final/blob/master/documents/Java%20Technology%20-%20Final%20Project%20Specification%20Hk1_2324.pdf)


## Desgin patterns

- Builder
- Strategy
- Factory Method (Simple Factory)
- Façade
- Singleton
- Command
- Template
- Decorator
- Adapter
- Chain of Responsibility

For detail: [Desgin Patterns Document](https://github.com/phuongtoan521h0486/POS_System_Java_final/blob/master/documents/pattern.pdf)



## Technology
**Framework:** Springboot

**Language:** Java, HTML/CSS, JavaScript

**Library:** Lombok, OpenCSV, Apache Poi, JavaMail

**Database:** MySQL

**API**: VNPay, Paypal, MoMo


## Installation

Enviroment requirement:
- Java 11
- IntelliJ IDEA
- XAMPP

Clone this repository

```bash
  git clone https://github.com/phuongtoan521h0486/POS_System_Java_final
```
or 
```bash
  git clone https://gitlab.duthu.net/521h0486/POS_System_Java_final
```
    
## Guide for import data to Database

- Run XAMPP and start Apache and MySql Service
- Go to [phpMyAdmin](http://localhost/phpmyadmin/index.php?route=/server/databases) page.
- Then import `pos_db.sql` file in `Database` folder.

## If you fail when import database, please try this: 
#### Changing php.ini

1. Navigate to `C:\xampp\php\php.ini`

2. Open `php.ini` file in a text editor.

3. Find the following configuration directives and update their values as follows:

   ```ini
   max_execution_time = 600
   max_input_time = 600
   memory_limit = 1024M
   post_max_size = 1024M
4. Save the php.ini file.

#### Changing my.ini
1. Navigate to `C:\xampp\mysql\bin\my.ini`

2. Open `my.ini` file in a text editor.

3. Find the following configuration directive and update its value as follows:

    ```ini
    max_allowed_packet = 1024M

4. Save the `my.ini` file.

#### Now you can import data to Database again

## Open Project with IntelliJ IDEA
- Click at play button
- You can access the website at http://localhost:8888
- Admin account: Username - admin, Password - 123456
## Support

For support, email 521h0486@student.tdtu.edu.vn


## Authors

- [@ToanGTV](https://github.com/phuongtoan521h0486)
- [@PhanHoang1205](https://github.com/phanhoang1205)

# Develop by GTV team ❤
Hope you enjoy this project
