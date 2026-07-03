# ParkingLotApp 🚗

Profesyonel ve nesne yönelimli programlama (OOP) prensiplerine uygun olarak geliştirilmiş Otopark Yönetim Sistemi. Bu proje, modern yazılım tasarım desenleri (Design Patterns) kullanılarak Java Swing ile masaüstü uygulaması olarak tasarlanmıştır.

## 🌟 Özellikler

- **Kullanıcı Doğrulama Sistemi**: Giriş (Login) ve Kayıt Ol (Register) ekranları ile güvenli kullanıcı erişimi. (`Giris.java`, `KayitOl.java`)
- **Kat Yönetimi**: Birden fazla otopark katı desteği (Kat 1, Kat 2) ve görsel araç park yeri takibi.
- **Rezervasyon Sistemi**: Kullanıcıların ileriye dönük park yeri ayırabilmesine olanak tanıyan rezervasyon modülü.
- **Dinamik Fiyatlandırma**: Saatlik ve günlük bazda çalışan esnek fiyatlandırma stratejileri (`HourlyPricingStrategy`, `DailyPricingStrategy`).
- **Kapsamlı Loglama**: Sistemdeki tüm olaylar (giriş, çıkış, rezervasyon vb.) loglanarak yönetici paneline raporlanır (`FileLogAdapter`, `LogsFrame`).
- **Yönetici Paneli**: Yöneticiler için kullanıcıları, aktif park durumlarını ve geçmiş logları izleyebilecekleri gelişmiş arayüz (`AdminPanel.java`).

## 🛠 Kullanılan Teknolojiler ve Tasarım Desenleri

Proje, yazılım mühendisliği standartlarına uygun şekilde aşağıdaki Tasarım Desenleri (Design Patterns) kullanılarak geliştirilmiştir:

- **Factory Pattern**: Otopark katlarının dinamik oluşturulması (`ParkingFloorFactory`).
- **Strategy Pattern**: Farklı fiyatlandırma algoritmalarının entegrasyonu (`PricingStrategy`).
- **Observer Pattern**: Sistemdeki park ve rezervasyon olaylarının dinlenmesi ve UI anlık güncellemeleri (`ParkingObserver`, `ParkingEventManager`).
- **Decorator Pattern**: Temel park servisinin loglama özellikleriyle sarmalanarak genişletilmesi (`ParkingServiceLogDecorator`).
- **Adapter Pattern**: Farklı loglama formatlarının sisteme entegrasyonu (`FileLogAdapter`).
- **Singleton Pattern**: Sistem genelinde tekil servis oturumları (`AuthService`, `Session`).

## 🚀 Kurulum ve Çalıştırma

### Gereksinimler
- Java Development Kit (JDK) 8 veya daha yeni bir sürüm.
- Herhangi bir Java destekli IDE (Eclipse, IntelliJ IDEA, VS Code vb.)

### Adımlar

1. **Repoyu Klonlayın:**
   ```bash
   git clone https://github.com/ibrahimumutcetin/ParkingLotApp.git
   ```
2. **Projeyi IDE'nize Aktarın:**
   - Eclipse veya tercih ettiğiniz bir IDE'yi açın.
   - Projeyi çalışma alanınıza dâhil edin (Import Project).
3. **Uygulamayı Başlatın:**
   - Uygulamanın başlangıç noktası (Main sınıfı) `src/ui/Giris.java` dosyasıdır.
   - `Giris.java` dosyasına sağ tıklayıp **Run As > Java Application** seçeneğiyle projeyi çalıştırabilirsiniz.
4. **Kullanım:**
   - İlk açılışta bir kullanıcı kaydı oluşturun.
   - Oluşturduğunuz kullanıcı bilgileri ile sisteme giriş yapın.
   - Katları görüntüleyin, boş park yerlerini seçerek park işlemi gerçekleştirin veya rezervasyon oluşturun.

## 📁 Proje Yapısı

- `src/ui/`: Kullanıcı arayüzünü (GUI) oluşturan sınıflar.
- `src/other/`: İş mantığını (Business Logic), servisleri ve tasarım desenlerini barındıran temel sınıflar.
- `src/icons/`: Arayüzdeki görsel öğeler.
- `*.txt`: Veri tabanı görevi gören ve program durumunu kaydeden metin dosyaları (`log.txt`, `users.txt`, `park.txt`, `reservation.txt`).

## 📜 Lisans

Bu proje eğitim amacıyla geliştirilmiş bir dönem projesidir. İhtiyacınıza göre geliştirip kullanabilirsiniz.
