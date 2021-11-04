package com.backmin;

import com.backmin.domains.member.domain.MemberRepository;
import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.menu.domain.MenuOption;
import com.backmin.domains.menu.domain.MenuOptionRepository;
import com.backmin.domains.menu.domain.MenuRepository;
import com.backmin.domains.order.domain.OrderRepository;
import com.backmin.domains.review.controller.ReviewController;
import com.backmin.domains.review.converter.ReviewConverter;
import com.backmin.domains.review.domain.ReviewRepository;
import com.backmin.domains.review.service.ReviewService;
import com.backmin.domains.store.domain.Category;
import com.backmin.domains.store.domain.CategoryRepository;
import com.backmin.domains.store.domain.Store;
import com.backmin.domains.store.domain.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DataSettingRunner implements ApplicationRunner {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuOptionRepository menuOptionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Category category1 = Category.of("한식");
        Category category2 = Category.of("치킨");
        Category category3 = Category.of("분식");
        List<Category> categories = List.of(category1, category2, category3);
        categoryRepository.saveAll(categories);

        List<Menu> menus1 = new ArrayList<>();
        List<Menu> menus2 = new ArrayList<>();
        List<Menu> menus3 = new ArrayList<>();
        List<Menu> menus4 = new ArrayList<>();
        List<Menu> menus5 = new ArrayList<>();
        List<Menu> menus6 = new ArrayList<>();
        List<Menu> menus7 = new ArrayList<>();
        List<Menu> menus8 = new ArrayList<>();

        // 명가 김밥
        menus1.add(Menu.of("낙지 덮밥", true, false, true, 8500, "흰 쌀밥위에 불향그윽한 낙지볶음듬뿍", List.of(MenuOption.of("계란 추가", 500))));
        menus1.add(Menu.of("차돌 된장찌개", true, false, true, 7500, "차돌된장찌개 + 공기밥", List.of(MenuOption.of("계란 추가", 500))));
        menus1.add(Menu.of("케이준 치킨 샐러드", true, false, true, 9500, "하우스 샐러드 + 후라이드 치킨", new ArrayList<>()));
        menus1.add(Menu.of("불고기 덮밥", false, false, true, 8500, "흰 쌀밥위에 맛있는 소고기가 살포시~", List.of(MenuOption.of("계란 추가", 500))));
        menus1.add(Menu.of("명가 김밥", false, false, true, 3500, "흰 쌀밥위에 맛있는 소고기가 살포시~", new ArrayList<>()));

        // -- 착한 전복
        menus2.add(Menu.of("갈비탕", true, false, true, 15000, "식사메뉴 + 김치 + 깍두기", new ArrayList<>()));
        menus2.add(Menu.of("전복 갈비탕", true, false, true, 19000, "식사메뉴 + 김치 + 깍두기", new ArrayList<>()));
        menus2.add(Menu.of("커플 쌈밥 세트", true, false, true, 30000, "메인 메뉴 2개선택 + 쌈 + 반찬 + 국", new ArrayList<>()));
        menus2.add(Menu.of("전복 물회", true, false, true, 18000, "식사메뉴 + 김치 + 깍두기", new ArrayList<>()));
        menus2.add(Menu.of("완도 전복죽", false, false, true, 15000, "전복죽 + 김치 + 장아찌", new ArrayList<>()));
        menus2.add(Menu.of("완도특 전복죽", false, false, true, 20000, "전복죽 + 김치 + 장아찌", new ArrayList<>()));
        menus2.add(Menu.of("전복버터구이(소)", false, false, true, 32000, "전복 5마리 + 튀김 + 잡채", new ArrayList<>()));
        menus2.add(Menu.of("전복버터구이(중)", false, false, true, 60000, "전복 10마리 + 튀김 + 잡채", new ArrayList<>()));

        // -- 연안 식당
        menus3.add(Menu.of("꼬막비빔밥", true, false, true, 12000, "연안의 대표메뉴 마성의 비빔밥", List.of(MenuOption.of("꼬막추가", 3000))));
        menus3.add(Menu.of("바삭한 해물파전", true, false, true, 15000, "배달시에는 바삭함이 줄어들어요ㅠ", new ArrayList<>()));
        menus3.add(Menu.of("꽃게살 비빔밥", true, false, true, 13000, "꽃게 한마리를 통채로 다 짜서 제공", new ArrayList<>()));
        menus3.add(Menu.of("어린이 꼬막비빔밥", false, false, true, 4500, "어린이들의 밥통령 영양만점", new ArrayList<>()));
        menus3.add(Menu.of("꼬막 무침", false, false, true, 22000, "꼬막양이 2배", new ArrayList<>()));
        menus3.add(Menu.of("활멍게무침", false, false, true, 22000, "갓잡은 활멍게를 소면사리까지", List.of(MenuOption.of("꼬막추가", 3000))));
        menus3.add(Menu.of("새우만두", false, false, true, 5000, "", new ArrayList<>()));

        // -- 배달 삼겹살
        menus4.add(Menu.of("++삼겹 맛집++ 고추세트", true, false, true, 14500, "고기 250G + 추억의 도시락 + 쌈장", List.of(MenuOption.of("고기 100g추가", 4000), MenuOption.of("고기 200g추가", 8000))));
        menus4.add(Menu.of("고냉세트", true, false, true, 15000, "고기 250G + 냉면 + 쌈장", List.of(MenuOption.of("고기 100g추가", 4000), MenuOption.of("고기 200g추가", 8000))));
        menus4.add(Menu.of("직화 고기 한판", true, false, true, 13000, "고기 + 쌈장 / 5칸 반찬", List.of(MenuOption.of("고기 100g추가", 4000), MenuOption.of("고기 200g추가", 8000))));
        menus4.add(Menu.of("직화 고기만 한상", true, false, true, 24000, "고기 + 5찬반찬 + 찌개 + 상추 + 콜라 [공기밥 불포함]", List.of(MenuOption.of("고기 100g추가", 4000), MenuOption.of("고기 200g추가", 8000))));
        menus4.add(Menu.of("파삼파삼", false, false, true, 15000, "350g", List.of(MenuOption.of("고기 100g추가", 4000), MenuOption.of("고기 200g추가", 8000))));
        menus4.add(Menu.of("매삼매삼", false, false, true, 15000, "350g", List.of(MenuOption.of("고기 100g추가", 4000), MenuOption.of("고기 200g추가", 8000))));
        menus4.add(Menu.of("치삼치삼", false, false, true, 15000, "350g", List.of(MenuOption.of("고기 100g추가", 4000), MenuOption.of("고기 200g추가", 8000))));

        // -- BBQ 사랑점
        menus5.add(Menu.of("황금올리브치킨", true, false, true, 18000, "비비큐 시그니처 메뉴 황금올리브치킨", new ArrayList<>()));
        menus5.add(Menu.of("황금올리브순살", true, false, true, 20000, "비비큐 시그니처 메뉴 황금올리브순살", new ArrayList<>()));
        menus5.add(Menu.of("황금올리브닭다리", true, false, true, 19000, "비비큐 시그니처 메뉴 황금올리브닭다리", new ArrayList<>()));
        menus5.add(Menu.of("황금올리브속안심", false, false, true, 17000, "닭 한 마리에서 두 조각만 나오는 고단백 부위! 속안심 후라이드", new ArrayList<>()));
        menus5.add(Menu.of("황올한 깐풍치킨", false, false, false, 19900, "BBQ 특제깐풍소스에 향긋한 대파와 고추를 곁들인 치맥의 절대 지존!", new ArrayList<>()));
        menus5.add(Menu.of("황올한 깐풍순살", false, false, false, 20900, "BBQ 특제깐풍소스에 향긋한 대파와 고추를 곁들인 치맥의 절대 지존!", new ArrayList<>()));
        menus5.add(Menu.of("핫 황금올리브 크리스피", true, false, true, 19000, "핫하고 빠삭한 맛 핫빠~ 크리스피 (HOT 매워요)", new ArrayList<>()));
        menus5.add(Menu.of("황금올리브반반", true, false, true, 19000, "비비큐 시그니처 메뉴 황금올리브반반", new ArrayList<>()));
        menus5.add(Menu.of("BBQ 모듬볼", false, false, false, 6000, "쇼콜라볼 2개 + 더블치즈볼 2개 + 고구마치즈볼 2개", new ArrayList<>()));
        menus5.add(Menu.of("BBQ소떡", false, false, false, 3000, "BBQ만의 소떡소떡!", new ArrayList<>()));

        // -- 죠스 떡볶이
        menus6.add(Menu.of("파티박스", true, true, true, 23500, "오늘 떡볶이 한 판 어때?! 즐거움을 한 판에 다 담았다. 죠스 파티박스", new ArrayList<>()));
        menus6.add(Menu.of("트윈세트", true, false, true, 14500, "떡볶이 + 수제튀김(중) + 순대 + 어묵 + 음료", new ArrayList<>()));
        menus6.add(Menu.of("스페셜세트", true, false, false, 17500, "떡볶이 + 수제튀김(대) + 순대 + 어묵 + 음료", new ArrayList<>()));
        menus6.add(Menu.of("패밀리세트", true, false, false, 21000, "떡볶이2 + 수제튀김(대) + 순대 + 어묵 + 음료", new ArrayList<>()));
        menus6.add(Menu.of("치즈세트", true, false, false, 16000, "치즈떡볶이 + 수제튀김(중) + 순대 + 어묵 + 음료", new ArrayList<>()));
        menus6.add(Menu.of("국물떡볶이세트(순한맛)", true, false, false, 15500, "국물떡볶이 + 수제튀김(중) + 순대 + 어묵 + 음료", new ArrayList<>()));
        menus6.add(Menu.of("반반떡볶이 (죠스떡볶이 + 치즈떡볶이)", false, false, false, 4500, "반반떡볶이 (죠스떡볶이 + 치즈떡볶이)", new ArrayList<>()));
        menus6.add(Menu.of("죠스떡볶이", false, false, true, 3000, "죠스떡볶이의 시그니처 메뉴", new ArrayList<>()));
        menus6.add(Menu.of("로제크림떡볶이", false, false, true, 4000, "죠스떡볶이의 신메뉴", new ArrayList<>()));
        menus6.add(Menu.of("죠스찰순대", false, false, true, 3500, "죠스의 쫀득한 찰순대", new ArrayList<>()));

        // -- 60계치킨
        menus7.add(Menu.of("[특급 신메뉴] 호랑이치킨", true, false, true, 18900, "호랑이치킨 + 마요네즈 + 치킨무1개 + 콜라500ml", new ArrayList<>()));
        menus7.add(Menu.of("[재주문율 1위] 고추 치킨", true, false, true, 18900, "특제간장소스에 고추로 버무러진 깔끔한 맛", new ArrayList<>()));
        menus7.add(Menu.of("간지 치킨", true, false, true, 18900, "특제 간장소스와 누릉지가 환상적으로 어우러짐", new ArrayList<>()));
        menus7.add(Menu.of("양념 치킨", true, false, true, 17900, "양념 끝판왕", new ArrayList<>()));
        menus7.add(Menu.of("순살 고추치킨", false, false, true, 18900, "", new ArrayList<>()));
        menus7.add(Menu.of("순살 간지치킨", false, false, true, 18900, "", new ArrayList<>()));
        menus7.add(Menu.of("순살 후라이드", false, false, true, 16900, "", new ArrayList<>()));

        // -- BHC 행복점
        menus8.add(Menu.of("하바네로 포테킹 후라이드 + 치즈볼", true, false, true, 21500, "포텐 폭발", new ArrayList<>()));
        menus8.add(Menu.of("뿌링클 콤보 + 치즈볼", true, false, true, 20000, "[윙/봉/닭다리 5조각씩 제공] 뿌링뿌링 세상에 없던 맛", new ArrayList<>()));
        menus8.add(Menu.of("골드킹 콤보 + 치즈볼", true, false, true, 20000, "[윙/봉/닭다리 5조각씩 제공] 단짠단짠, 숙성간장과 꿀의 황금비율", new ArrayList<>()));
        menus8.add(Menu.of("맛초킹 콤보 + 치즈볼", true, false, true, 20000, "[윙/봉/닭다리 5조각씩 제공] 매콤짭짤, 밥과 먹기에 딱 좋은 완벽조합", new ArrayList<>()));
        menus8.add(Menu.of("후라이드 윙", false, false, true, 18000, "", new ArrayList<>()));
        menus8.add(Menu.of("후라이드 스틱", false, false, true, 19000, "", new ArrayList<>()));
        menus8.add(Menu.of("뿌링맵소킹 콤보", false, false, true, 18900, "", new ArrayList<>()));

        menuRepository.saveAll(menus1);
        menuRepository.saveAll(menus2);
        menuRepository.saveAll(menus3);
        menuRepository.saveAll(menus4);
        menuRepository.saveAll(menus5);
        menuRepository.saveAll(menus6);
        menuRepository.saveAll(menus7);
        menuRepository.saveAll(menus8);

        Store store1 = Store.of("명가김밥","02123123", "각 분야의 전문 요리사들이 주문 접수오 동시에 조리를 시작합니다.", 5000, 29, 44, 3500, true, true, category1, menus1);
        Store store2 = Store.of("착한전복","031100011","삼계탕 주문시 국물을 요청하시면 함께 보내드리도록 하겠습니다.",14900, 30, 45, 2000, true, true, category1, menus2);
        Store store3 = Store.of("연안식당","123415153", "꼬막 1등 브랜드, 육계장 1등 브랜드",9000, 39, 54,  2900, true, true, category1, menus3);
        Store store4 = Store.of("배달 삼겹살","12351234", "저도 하루한끼 삼겹살을 먹습니다.",9000, 29, 44,  3800, true, true, category1, menus4);
        Store store5 = Store.of("60계치킨","125324", "환영합니다 60계 치킨입니다.",16000, 42, 57,  2000, true, true, category2, menus5);
        Store store6 = Store.of("BHC 행복점","35352111", "",15000, 39, 54,  3800, true, true, category2, menus6);
        Store store7 = Store.of("BBQ 사랑점","141231", "BBQ 사랑점 오픈 이벤트 진행중",11000, 50, 65,  3800, true, true, category2, menus7);
        Store store8 = Store.of("죠스떡볶이","53451","배달 주문 시 모바일 쿠폰 사용 안됩니다.", 17500, 39, 54,  0, true, true, category3, menus8);
//        Store store9 = Store.of("동대문 엽기떡볶이","9000", "부득이하게 배달료를 인상하게 되었습니다.",9000, 47, 62,  1500, true, true, category3, menus9);
        List<Store> stores = List.of(store1, store2, store3, store4, store5, store6, store7, store8);
        storeRepository.saveAll(stores);
    }

}
