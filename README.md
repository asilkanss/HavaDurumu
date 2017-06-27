# HavaDurumu

## Hava durumu console java uygulaması
**İşlevi:** *Accuweather API*'sini kullanarak her on saniyede bir *Quartz Scheduler* kütüphanesiyle İstanbul ve Aydın illerinin hava durumu datalarını *JSON* olarak çekip parse ettirdikten sonra, *Hibernate* ile *Ms SQL*'e CRUD işlemlerinin *Java Threadler* kullanılarak paralel bir şekilde yapılması.

### HavaDurumu.java(Main Class)
**36.** satırda bulunan `"0/10 * * * * ?"` formatı Quartz Scheduler kütüphanesinin hangi zaman aralığında çalışacağını belirtiyor.
Bu formatı [buraya tıklayarak](http://www.cronmaker.com/) oluşturabilirsiniz.

### QuartzJob.java
**42-61.** satırlar arası Scheduler çalışması istediğimiz durumları bu fonksiyon içine yazdım.
<br>**63-80.** satırlar arası Hibernate ile veritabanına yazma işlemi yapılıyor.

### paralel.java
**35-59.** satırlar arası okunan JSON'u objelere bölüp parse etme işlemi.
<br>**6-67.** satırlar arası threadlerin kullanılması
<br>**69-86.** satırlar arası JSON API'si kullanabilmek için URL okuma fonksiyonu.

### HavaDurumu2.java
URL API okuyabilmek için diğer bir yol
