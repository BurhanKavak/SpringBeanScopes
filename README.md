# :triangular_flag_on_post: Spring Bean Scope

<b> Spring Framework, nesnelerin yaşam döngülerini kontrol etmek için bir dizi "scope" (kapsam) sağlar. Spring Scope'lar, bir nesnenin ne kadar süreyle var olacağını ve hangi durumlarda yaratılacağını belirleyen bir mekanizmadır.</b>

Spring Framework'ün bazı Scope'ları şunlardır: 

1. **Singleton:** Bu, Spring'in varsayılan kapsamıdır. Bir Singleton nesnesi, uygulama başlatıldığında yaratılır ve uygulama kapatılana kadar varlığını sürdürür. Aynı Singleton nesnesi, uygulama boyunca tüm taleplerde kullanılır.
2. **Prototype:** Bir Prototype nesnesi, her talep edildiğinde yaratılır. Bu, her talep edildiğinde yeni bir nesne oluşturulduğu anlamına gelir. Prototype nesneleri, her talep edildiğinde bir önceki durumundan tamamen bağımsızdır.
3. **Request:** Bir Request nesnesi, bir HTTP isteği geldiğinde yaratılır ve yanıt gönderildiğinde yok edilir. Bu, bir Request nesnesinin yalnızca bir HTTP isteği sırasında kullanıldığı anlamına gelir.
4. **Session:** Bir Session nesnesi, bir kullanıcının oturum açtığı anda yaratılır ve kullanıcı oturumunu sonlandırdığında yok edilir. Bu, bir Session nesnesinin kullanıcının oturumu boyunca kullanıldığı anlamına gelir.
5. **Global Session:** Bir Global Session nesnesi, bir portlet uygulamasında kullanılır ve tüm kullanıcıların paylaşabileceği özellikleri tutmak için tasarlanmıştır. Global Session nesnesi, kullanıcıların portlet sayfaları arasında gezinirken de varlığını sürdürür.

Scope'lar, nesnelerin yaratılması, kullanımı ve yok edilmesiyle ilgili farklı senaryoları ele almak için kullanılır. Bu sayede, Spring uygulamaları daha esnek ve ölçeklenebilir hale gelir.


## Spring Scope olarak en yaygın kullanılan Singleton ve Prototype’a bakalım :

## 🎯 Spring Scope Singleton

<b>Singleton Scope, Spring'in varsayılan olarak kullandığı scope'dur. Bu scope'ta, bir bean nesnesi sadece bir kez oluşturulur ve bu nesne, uygulamanın yaşam döngüsü boyunca tek bir örnek olarak kullanılır.

Örneğin, bir "UserRepository" adında bir bean tanımladığımızı ve bu bean'in singleton scope'da yönetilmesini istediğimizi düşünelim: </b>

```java
@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserRepositoryImpl implements UserRepository {
    // ...
}
```

<b>Burada, **`@Scope`** annotation'ı kullanılarak **`ConfigurableBeanFactory.SCOPE_SINGLETON`** değeri verilerek, bean'imizin Singleton Scope'da yönetilmesi istendiği belirtiliyor.

Bu UserRepository bean'i, uygulamanın herhangi bir yerinde talep edildiğinde, aynı instance kullanılacak ve herhangi bir state değişikliği tüm uygulama boyunca geçerli olacaktır. Bu durumda, örneğin veritabanı bağlantısı gibi bir kaynak açılıp, bağlantı sürdürüldüğünde bu bağlantı, uygulama sonlandırılıncaya kadar aktif kalacak ve uygulama genelinde paylaşılacaktır.

Singleton Scope, uygulamanın genelinde ortak kullanılması gereken nesnelerin yönetimi için uygun bir seçenektir. Ancak, büyük boyutlu nesneler veya çok sık kullanılmayan nesneler bu scope altında yönetilmemelidir, çünkü gereksiz yere bellek kullanımına neden olabilirler.</b>


## 🎯 Spring Scope Prototype

<b>Prototype scope'da, her talep edildiğinde Spring, yeni bir nesne örneği oluşturur. Böylece her talep edildiğinde farklı bir bean nesnesi oluşturulur ve her bir nesne örneği kendi bağımsız durumunu tutar. Bu durum, özellikle büyük boyutlu veya sürekli değişen nesnelerin yönetimi için uygundur.

Örneğin, "OrderService" adında bir bean tanımlayalım ve bu bean'in Prototype Scope'da yönetilmesini istediğimizi varsayalım:</b>


```java
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OrderService {
    // ...
}
```

<b>Burada, **`@Scope`** annotation'ı kullanılarak **`ConfigurableBeanFactory.SCOPE_PROTOTYPE`** değeri verilerek, bean'imizin Prototype Scope'da yönetilmesi istendiği belirtiliyor.

Bu OrderService bean'i, her bir talep edildiğinde Spring tarafından yeni bir örnek oluşturulacak ve her örnek, sadece o talep üzerinden kullanılacak ve başka taleplerde kullanılmayacaktır. Bu durum, her talebin kendi bağımsız state'ini tutması için uygun bir seçenektir.

Prototype Scope, genellikle, çok sık değişen, büyük boyutlu veya farklı taleplerde farklı state tutması gereken nesnelerin yönetimi için uygun bir seçenektir. Ancak, her talep edildiğinde yeni bir örnek oluşturulduğu için, gereksiz bellek kullanımına yol açabileceğinden özenli kullanılmalıdır.
</b>

## :small_red_triangle_down: Yaptığımız projedeki örneği inceleyecek olursak : :small_red_triangle_down:

### Singleton için sonuç 

![image](https://user-images.githubusercontent.com/79043326/223666276-fd69b6e9-9d02-4a63-8135-ceccb1e83005.png)



> Sonucu inceleyecek olursak 'hello' ve 'hello2' referansını kullanarak getName() yöntemini çağırdığımızda aynı çıktıları alıyoruz.<br>
> Bu, her iki referansın da aynı nesnenin getName() yöntemini çağırdığı anlamına gelir. <br>
> Ayrıca, "hello" ve "hello2" referansını if bloğu ile karşılaştırdığımızda çıktı "true" olduğundan birbirine eşittir yazısı gözükmektedir, bu da aynı nesnenin "hello" ve "hello2" arasında paylaşıldığı anlamına gelir.<br>
> Yani ilk defa istek yaptığımızda yeni bir bean örneği (HelloWorld) yaratıldığı ve her yeni istek için sürekli nesne yaratmak yerine aynı nesnenin paylaşıldığı açıktır.

### Prototype  için sonuç 

![image](https://user-images.githubusercontent.com/79043326/223666409-9f532658-86cc-437a-9131-589a75a34eb4.png)


> Sonucu inceleyecek olursak 'hello' ve 'hello2' referansını kullanarak getName() yöntemini çağırdığımızda, farklı çıktılar alıyoruz, yani her iki referans da farklı bir nesnenin getName() yöntemini çağırıyor.<br>
> Ayrıca, "hello" ve "hello2" referanslarını karşılaştırdığımızda çıktı "false" olduğundan birbirine eşit değiller yazısı gözükmektedir, bu da her iki referansın da farklı bir nesneye atıfta bulunduğu anlamına gelir. <br>
> Bu bean için yapılan her istekte yeni bir bean örneğinin (HelloWorld) yaratıldığı açıktır.<br>

### Farklarını inceleyecek olursak 

| Singleton | Prototype |
| --- | --- |
| Spring IoC container tek bir bean tanımı için yalnızca bir örnek oluşturur. | Bu bean için her istek yapıldığında, tek bir bean tanımı için yeni bir örnek oluşturulur. |
| O bean’e yapılan her istekte aynı nesne paylaşılır. yani her enjekte edildiğinde aynı nesne döndürülür. | Her yeni istek için yeni bir örnek oluşturulur. yani her enjekte edildiğinde yeni bir nesne yaratılır. |
| Bir bean'in varsayılan kapsamı singleton'dur. Bu nedenle, açıkça tekil olarak belirtmemize gerek yok. | Varsayılan olarak kapsam prototype değildir, bu nedenle bir bean’in kapsamını açık bir şekilde prototype olarak bildirmeniz gerekir. |


## :pushpin: XML Dosyasında scope kısmında "singleton" veya "prototype" şeklinde belirterek deneyebilirsiniz.

```xml
<bean
         id = "hw"
         class= "scopeSingletonVsPrototype.HelloWorld"
         scope = "prototype" > </bean>
```






