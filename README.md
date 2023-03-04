<h2>#Для ВКонтакте</h3>
<h3>Используемый стек:</h3>
<ul>
 <li>Retrofit</li>
 <li>Room</li>
 <li>Glide</li>
 <li>Hilt</li>
 <li>LiveData</li>
 <li>ViewModel</li>
 <li>Fragment</li>
 <li>Coroutines</li>
 <li>Paging</li>
 <li>MVVM</li>
</ul>
<p><h3>Описание</h3></p>
При запуске моего приложения перед пользователем появится экран "Trending", он содержит 25 гифок, информация о них сохраняется в RoomDataBase.

![Screenshot_20230304_151547_GifApp](https://user-images.githubusercontent.com/86302070/222900792-2fce445e-2691-41d3-9157-9e81d3297a97.jpg)

При нажатии на один из элементов, открывается экран с карточкой гифки, выводится сама гифка, её название, возрастное ограничение(адаптированное с американского на русское), 
ник пользователя, который загрузил гифку.

![Screenshot_20230304_151558_GifApp](https://user-images.githubusercontent.com/86302070/222900980-f2daecb1-f8d6-44b9-a145-5bda982b319a.jpg)

При нажатии на лупу, пользователю предоставляется возможность ввести запрос и найти новые гифки. 

![Screenshot_20230304_151611_GifApp](https://user-images.githubusercontent.com/86302070/222901040-a5727324-5ac8-41b0-bd7f-4ad9955f9979.jpg)

Также можно просмотреть карточку.

![Screenshot_20230304_151616_GifApp](https://user-images.githubusercontent.com/86302070/222901078-173a9b33-2141-45c8-89f8-71f8542f221c.jpg)

В фрагменте Search реализована пагинация, то есть почти бесконечная лента(пока сайт даёт), вот они, подгружаются такие:

![Screenshot_20230304_151624_GifApp](https://user-images.githubusercontent.com/86302070/222901129-c658ed67-9fd1-459c-90dc-f072cb92415f.jpg)

В приложении реализован NetworkChecker, поэтому при отсутствии соединения, пользователь будет оповещён об этом, а как вернётся в приложение, новые гифки автоматически подгрузятся.

![Screenshot_20230304_151838_GifApp](https://user-images.githubusercontent.com/86302070/222901291-ba7dd422-5917-443c-bef4-ad5e5512c03a.jpg)

<h1>Конец</h1>

![image](https://user-images.githubusercontent.com/86302070/222901353-aa4caffd-c3d3-445c-8ca1-e587d7a9ddc6.png)

