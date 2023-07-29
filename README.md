# Wayfinding Soldier 迷走士兵
**_透過士兵走訪實作深度優先DFS、廣度優先BFS以及損失優先UCS演算法_**

## 遊戲預覽（Preview of Game）
![image](https://user-images.githubusercontent.com/61927641/189267435-d9948980-6cc9-4403-971b-8b93acc265f4.png)
> 左至右分別為士兵以DFS、BFS以及UCS尋路走訪

## 功能設計（Design of functionality）
1. 使用者可以透過滑鼠點擊及鍵盤按鍵來選擇在地圖上創建不同的建築物，此功能透過點擊創建多個執行緒，藉此達到可一次蓋不同的房子。
2. 使用者可以透過滑鼠點擊房子來生產士兵（房子下方需有空位），並且同時也能利用拖拉滑鼠達到拖動地圖的效果。
3. 使用者可以待士兵生成後，選取士兵後再點選目的地，則其可以依一開始輸入之三種演算法 DFS、BFS 以及 UCS之一，抵達所選之目的地。

## 使用說明（Instructions of use）：
1. 需先按照下方資料表資訊、綱要以及資料字典於本機建立PostgreSQL資料表。

    > 資料表名稱　：WayfindingSoldier  
    > 資料表使用者：WayfindingSoldierUser  
    > 資料表密碼　：WayfindingSoldierPwd  
    <img width="400" alt="DB_schema" src="https://github.com/ben03181574/Wayfinding-Soldier/assets/61927641/041de64c-e662-455f-b3a1-6793422f723e">
    <img width="400" alt="DB_dictionary" src="https://github.com/ben03181574/Wayfinding-Soldier/assets/61927641/57ed6937-ebd8-495f-bd5a-5aeaa270644d">  
2. **執行 A1083341_checkpoint7.jar** ，執行時須於後方**加入三項參數**，分別為欲載入的地圖編號、地圖縮放程度以及士兵移動演算法編號。  
4. 執行成功後，使用者可以按鍵以及點擊螢幕以產生不同類型的房屋  

   > 按 ‘ b ‘ 即可建立 1 * 1 的軍營。  
   > 按 ‘ h ’ 即可建立 1 * 1 的房子。  
   > 按其他鍵即可建立 2 * 2 的金字塔。

## OOP的考量（Consideration of Object-Oriented Programing）

* A1083341_ checkpoint7_QueryDB：
  * 將障礙物位置資訊以及障礙物的樣式和照片路徑查詢出來後將值存入物件變數以及 ArrayList 和 hashmap。
* A1083341_ checkpoint7_GamePanel：
  * 須利用使窗大小、照片大小計算出需繪製之圖片的起始位置。每個房屋的物件須利用 setLocation()設定該 label 的位置。
* A1083341_ checkpoint7_GameFrame：
  * 建立 panel 物件，已顯示出 panel 內容，同時為了讓使用者可以藉由拖動達成移動地圖之需求，需註冊滑鼠事件，利用滑鼠放開以及點擊計算出移動距離，同時將中心點加上移動距離後即可根據使用者拖動幅度移動地圖。而當使用者完成一次點擊時，需計算出該位置屬於地圖網格中第幾格，確定該位置可以建置房屋後，利用 add 將此房屋的 label 新增至此panel 中。
* A1083341_checkpoint7_Game：
  * 此為主程式，須包含建立資料庫連接、查詢的物件，以便將資料查詢出來給 panel 使用以繪製出地圖，達成題目要求
* A1083341_checkpoint7_House：
  * 此為建築物 house 的類別，玩家點及時會建立此物件並進入其中之 run函式來進行切換建構%數，同時設定滑鼠監聽，當點擊 house label 時，將 spawnMenu 顯示出來。
* A1083341_checkpoint7_Pyramid：
  * 此為建築物 house 的類別，玩家點及時會建立此物件並進入其中之 run函式來進行切換建構%數。
* A1083341_checkpoint7_Barrack：
  * 此為建築物 house 的類別，玩家點及時會建立此物件並進入其中之 run函式來進行切換建構%數。
* A1083341_checkpoint7_SpawnMenu：
  * 此類別用以顯示建構士兵之按鈕，同時設定監聽相關按鈕點擊事件，當點擊按鈕時，新增出 Solider 物件並將其新增 Thread 中同時運行此Thread。
* A1083341_checkpoint7_Soldier_Movement：
  * Solider 類別的 interface 用以確保有時做出 startMove()、detectRoute()兩個 method。
* A1083341_checkpoint7_Soldier：
  * 此類別設有目前位置以及目標位置還有目前是否有被選取的布林值，當此條 Solider 的 Thread 被通知可以運行時，可以利用其中的detectRoute，使用目前位置以及目標位置進行計算達到目的地之路徑，再利用 startMove()確保下一步不會有障礙物後進行移動並重畫。
* A1083341_checkpoint7_Block：
  * 此類別存有目前之位置以及此位置之類型以及花費。設有獲取以及設定以上料之方法。
* A1083341_checkpoint7_BlockPriorityQueue：
此類別為為了實作 UCS 之演算法。
* A1083341_checkpoint7_BlockQueue：
  * 此類別為為了實作出 BFS 之演算法。
* A1083341_checkpoint7_BlockStack：
  * 此類別為為了實作出 DFS 之演算法。
* A1083341_checkpoint7_Fringe：
  * 以上三種類別皆實作此介面，設有新增、刪除、判斷是否為空之方法。
* A1083341_checkpoint7_Node：
  * 此類別存有目前之位置以及下一個 Node 的物件。設有獲取以及設定以上料之方法
* A1083341_checkpoint7_RouteFinder：
  * 此類別為為了找出路經。其設有搜尋、拓展方法，以上兩種方法為為了實作出個演算法所需之操作。而 createRoute 方法則是將搜尋到之路徑以鏈結串列形式回傳。
* A1083341_checkpoint7_RouteLinkedList：
  * 此類別為讓士兵能根據此鏈結串列來得知移動步驟，設有新增、刪除、插入、回傳長度、以及設定及獲取 head 的方法。
