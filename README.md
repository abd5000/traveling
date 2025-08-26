# Traveling System (Kotlin)

نظام حجز متكامل مكوّن من **3 تطبيقات أندرويد** (كوتلن):
- 🛠️ **myAdmin**: لوحة تحكم الأدمن لإدارة الطلبات والسائقين والمستخدمين.
- 🚗 **myDriver**: تطبيق السائق لارسال الموقع بالوقت الحقيقي وعرض معلومات الرحلة.
- 📱 **myUser**: تطبيق المستخدم لحجز مقاعد في الحافلة  .

---

## 👨‍💻 المطوّر
- الاسم: **عبد الباسط قاق**
- LinkedIn: [abdul-basit-qaq](https://www.linkedin.com/in/abdul-basit-qaq-513321237?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app)
- GitHub: [abd5000](https://github.com/abd5000)

---

## 🧰 التقنيات المستخدمة
- **Kotlin**, **XML layouts**
- **MVVM**, **LiveData**, **Coroutines**
- **Retrofit2** (REST)
- **Firebase Cloud Messaging** (إشعارات)
- **Google Maps SDK** (خرائط)
- **Socket.IO** (تتبع لحظي Real-time)
- **Node.js backend** (موجود لدي محليًا – غير مرفوع ضمن هذا المستودع)

> **ملاحظة:** الجزء الخاص بـ **Node.js** غير مرفوع هنا. التطبيقات تعتمد على خادم (Server) خارجي لـ Socket.IO وواجهات REST.

---

## 📂 هيكل المجلدات
traveling/
├── myAdmin/ # تطبيق الأدمن
├── myDriver/ # تطبيق السائق
└── myUser/ # تطبيق المستخدم

---

## ⚙️ الإعداد والتشغيل
1) افتح أي تطبيق (myAdmin أو myDriver أو myUser) في **Android Studio**.
2) نفّذ **Sync Gradle**.
3) عدّل إعدادات الاتصال:
   - عنوان الخادم (Base URL) الخاص بـ **Retrofit**.
   - عنوان Socket.IO (مثلاً `wss://your-server/socket`).
4) أضِف ملف **google-services.json** (لم يتم تضمينه في GitHub) لكل تطبيق يستخدم FCM.
5) شغّل التطبيق على محاكي أو جهاز حقيقي.

> **أسرار/مفاتيح:** لا تقم برفع مفاتيح Google Maps أو ملفات الاعتماد إلى GitHub. استخدم `local.properties` أو بيئة CI سرّية.

---

## ✅ الميزات الرئيسية (مختصر)
- تتبع السائق **بالوقت الحقيقي** عبر Socket.IO.
- إدارة الطلبات والسائقين عبر تطبيق **Admin**.
- **إشعارات فورية** للمستخدمين والسائقين (FCM).
- بنية **MVVM** مع **LiveData** و**Coroutines**.
- تكامل **خرائط جوجل** لعرض المواقع والمسارات.

---

## 🔖 كلمات مفتاحية (Topics)
`kotlin` · `android` · `mvvm` · `retrofit2` · `socket.io` · `firebase` · `google-maps` · `livedata` · `coroutines`

---

## 🌍 English (brief)
A 3-app Android travel system in Kotlin: **Admin**, **Driver**, and **User**.  
Tech: MVVM, LiveData, Coroutines, Retrofit2, FCM notifications, Google Maps, real-time tracking via Socket.IO.  
Backend: **Node.js** (not included in this repo).
