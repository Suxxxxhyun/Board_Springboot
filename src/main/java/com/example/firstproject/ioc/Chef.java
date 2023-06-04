package com.example.firstproject.ioc;

import org.springframework.stereotype.Component;

@Component
public class Chef {

    //셰프는 식재료 공장을 알고있음.
    private IngredientFactory ingredientFactory;

    // 셰프가 식재료 공장과 협업하기 위한 DI
    // DI(의존성주입) - 동작에 필요한 객체를 외부에서 받아옴.
    public Chef(IngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    public String cook(String menu) {
        // 1. 요리 재료 준비
        //Pork pork = new Pork("한돈 등심");
        //Beaf beaf = new Beaf("한우 꽃등심");
        Ingredient ingredient = ingredientFactory.get(menu);

        // 2. 요리 반환
        //return pork.getName() + "으로 만든 " + menu;
        return ingredient.getName() + "으로 만든 " + menu;
    }
}
