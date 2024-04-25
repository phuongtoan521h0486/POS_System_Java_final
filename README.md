
# POS System Java website

This project is the final assignment for the Java Technology course.

![Logo](https://southeastasia1-mediap.svc.ms/transform/thumbnail?provider=spo&inputFormat=png&cs=fFNQTw&docid=https%3A%2F%2Ftgmteam-my.sharepoint.com%3A443%2F_api%2Fv2.0%2Fdrives%2Fb!DfmtAVBc_E-VvYVI72R8VhQMqwOFtJ5LquAsAkRs8DsYXW3v0NgOT6ICdcMPqSqq%2Fitems%2F01DNCHNAP5X7VNMT2S5NH2VVDWLA6LOPJI%3Fversion%3DPublished&access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAvdGdtdGVhbS1teS5zaGFyZXBvaW50LmNvbUA3N2I0NzY3MS00ZDNhLTQ3YTgtODM2MS04NTM1MTZkZGM3ODEiLCJjYWNoZWtleSI6IjBoLmZ8bWVtYmVyc2hpcHwxMDAzMjAwMTk1NTA3MzhlQGxpdmUuY29tIiwiZW5kcG9pbnR1cmwiOiJaOTVuM3Q4Q3doTE1CWXdtc3o2Z3BQR0tsb1JadU5aUVU4ZUhuVXdlOFF3PSIsImVuZHBvaW50dXJsTGVuZ3RoIjoiMTE3IiwiZXhwIjoiMTcxMzkwNjAwMCIsImlwYWRkciI6IjIwMDE6ZWUwOjRmY2M6NTQ4MDo0ODMzOmYxOTM6NmMxNzo1ZGU4IiwiaXNsb29wYmFjayI6IlRydWUiLCJpc3MiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAiLCJpc3VzZXIiOiJ0cnVlIiwibmFtZWlkIjoiMCMuZnxtZW1iZXJzaGlwfHRvYW5ndHZAdGdtdGVhbS5vbm1pY3Jvc29mdC5jb20iLCJuYmYiOiIxNzEzODg0NDAwIiwibmlpIjoibWljcm9zb2Z0LnNoYXJlcG9pbnQiLCJzaWQiOiJkYWRhNjg1MC03MzI5LTRjMmEtOWY4Yi01MDQ0ZmFhNzNjM2MiLCJzaWduaW5fc3RhdGUiOiJbXCJrbXNpXCJdIiwic2l0ZWlkIjoiTURGaFpHWTVNR1F0TldNMU1DMDBabVpqTFRrMVltUXRPRFUwT0dWbU5qUTNZelUyIiwic25pZCI6IjYiLCJzdHAiOiJ0IiwidHQiOiIwIiwidmVyIjoiaGFzaGVkcHJvb2Z0b2tlbiJ9.pB4DVIElcdfhz91_l3H2vIt0OdoJFvuhWTtrGFGICK0&cTag=%22c%3A%7BD6EABFFD-524F-4FEB-AAD4-76583CB73D28%7D%2C2%22&encodeFailures=1&width=229&height=229&srcWidth=2978&srcHeight=2978)


## Features

- Account management
- Product Catalog Management (Admin only)
- Customer Management
- Transaction Processing
- Reporting and Analytics

For detail: [Specification Requirements](https://tgmteam-my.sharepoint.com/:b:/g/personal/toangtv_tgmteam_onmicrosoft_com/EfYIFcO0TBJMsqSyBeHc_CoBIkqfQI3Xcf9GM4esnHwkjA?e=J9g3zQ)


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

For detail: [Desgin Patterns Document](https://linktodocumentation)



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

![App Screenshot](https://southeastasia1-mediap.svc.ms/transform/thumbnail?provider=spo&inputFormat=png&cs=fFNQTw&docid=https%3A%2F%2Ftgmteam-my.sharepoint.com%3A443%2F_api%2Fv2.0%2Fdrives%2Fb!DfmtAVBc_E-VvYVI72R8VhQMqwOFtJ5LquAsAkRs8DsYXW3v0NgOT6ICdcMPqSqq%2Fitems%2F01DNCHNAJ64NJTCBF5RFGKSW3EWUOHIDYW%3Fversion%3DPublished&access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAvdGdtdGVhbS1teS5zaGFyZXBvaW50LmNvbUA3N2I0NzY3MS00ZDNhLTQ3YTgtODM2MS04NTM1MTZkZGM3ODEiLCJjYWNoZWtleSI6IjBoLmZ8bWVtYmVyc2hpcHwxMDAzMjAwMTk1NTA3MzhlQGxpdmUuY29tIiwiZW5kcG9pbnR1cmwiOiJaOTVuM3Q4Q3doTE1CWXdtc3o2Z3BQR0tsb1JadU5aUVU4ZUhuVXdlOFF3PSIsImVuZHBvaW50dXJsTGVuZ3RoIjoiMTE3IiwiZXhwIjoiMTcxMzkwNjAwMCIsImlwYWRkciI6IjIwMDE6ZWUwOjRmY2M6NTQ4MDo0ODMzOmYxOTM6NmMxNzo1ZGU4IiwiaXNsb29wYmFjayI6IlRydWUiLCJpc3MiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAiLCJpc3VzZXIiOiJ0cnVlIiwibmFtZWlkIjoiMCMuZnxtZW1iZXJzaGlwfHRvYW5ndHZAdGdtdGVhbS5vbm1pY3Jvc29mdC5jb20iLCJuYmYiOiIxNzEzODg0NDAwIiwibmlpIjoibWljcm9zb2Z0LnNoYXJlcG9pbnQiLCJzaWQiOiJkYWRhNjg1MC03MzI5LTRjMmEtOWY4Yi01MDQ0ZmFhNzNjM2MiLCJzaWduaW5fc3RhdGUiOiJbXCJrbXNpXCJdIiwic2l0ZWlkIjoiTURGaFpHWTVNR1F0TldNMU1DMDBabVpqTFRrMVltUXRPRFUwT0dWbU5qUTNZelUyIiwic25pZCI6IjYiLCJzdHAiOiJ0IiwidHQiOiIwIiwidmVyIjoiaGFzaGVkcHJvb2Z0b2tlbiJ9.pB4DVIElcdfhz91_l3H2vIt0OdoJFvuhWTtrGFGICK0&cTag=%22c%3A%7B3153E33E-BD04-4C89-A95B-64B51C740F16%7D%2C1%22&encodeFailures=1&srcWidth=&srcHeight=&width=668&height=433&action=Access)

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

![App Screenshot](https://southeastasia1-mediap.svc.ms/transform/thumbnail?provider=spo&inputFormat=png&cs=fFNQTw&docid=https%3A%2F%2Ftgmteam-my.sharepoint.com%3A443%2F_api%2Fv2.0%2Fdrives%2Fb!DfmtAVBc_E-VvYVI72R8VhQMqwOFtJ5LquAsAkRs8DsYXW3v0NgOT6ICdcMPqSqq%2Fitems%2F01DNCHNAIMQ7JTKPAMKNCZETD6VOCLTY74%3Fversion%3DPublished&access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAvdGdtdGVhbS1teS5zaGFyZXBvaW50LmNvbUA3N2I0NzY3MS00ZDNhLTQ3YTgtODM2MS04NTM1MTZkZGM3ODEiLCJjYWNoZWtleSI6IjBoLmZ8bWVtYmVyc2hpcHwxMDAzMjAwMTk1NTA3MzhlQGxpdmUuY29tIiwiZW5kcG9pbnR1cmwiOiJaOTVuM3Q4Q3doTE1CWXdtc3o2Z3BQR0tsb1JadU5aUVU4ZUhuVXdlOFF3PSIsImVuZHBvaW50dXJsTGVuZ3RoIjoiMTE3IiwiZXhwIjoiMTcxMzkwNjAwMCIsImlwYWRkciI6IjIwMDE6ZWUwOjRmY2M6NTQ4MDo0ODMzOmYxOTM6NmMxNzo1ZGU4IiwiaXNsb29wYmFjayI6IlRydWUiLCJpc3MiOiIwMDAwMDAwMy0wMDAwLTBmZjEtY2UwMC0wMDAwMDAwMDAwMDAiLCJpc3VzZXIiOiJ0cnVlIiwibmFtZWlkIjoiMCMuZnxtZW1iZXJzaGlwfHRvYW5ndHZAdGdtdGVhbS5vbm1pY3Jvc29mdC5jb20iLCJuYmYiOiIxNzEzODg0NDAwIiwibmlpIjoibWljcm9zb2Z0LnNoYXJlcG9pbnQiLCJzaWQiOiJkYWRhNjg1MC03MzI5LTRjMmEtOWY4Yi01MDQ0ZmFhNzNjM2MiLCJzaWduaW5fc3RhdGUiOiJbXCJrbXNpXCJdIiwic2l0ZWlkIjoiTURGaFpHWTVNR1F0TldNMU1DMDBabVpqTFRrMVltUXRPRFUwT0dWbU5qUTNZelUyIiwic25pZCI6IjYiLCJzdHAiOiJ0IiwidHQiOiIwIiwidmVyIjoiaGFzaGVkcHJvb2Z0b2tlbiJ9.pB4DVIElcdfhz91_l3H2vIt0OdoJFvuhWTtrGFGICK0&cTag=%22c%3A%7B35D3870C-0C3C-4553-924C-7EAB84B9E3FC%7D%2C1%22&encodeFailures=1&width=362&height=85&srcWidth=362&srcHeight=85)

- Click at play button
- You can access the website at http://localhost:8888
- Admin account: Username - admin, Password - 123456
## Support

For support, email 521h0486@student.tdtu.edu.vn


## Authors

- [@ToanGTV](https://github.com/phuongtoan521h0486)


# Develop by GTV team ❤
Hope you enjoy this project
