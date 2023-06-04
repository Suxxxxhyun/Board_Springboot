package com.example.firstproject.objectmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BurgerTest {

    @Test
    public void 자바_객체를_JSON으로_변환() throws JsonProcessingException {

        // 1. 준비
        //스프링에서 JSON -> DTO, DTO -> JSON 변환해주는 ObjectMapper를 제공해주고 있음.
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> ingredients = Arrays.asList("통새우 패티", "순쇠고기 패티", "토마토", "어니언 소스");
        Burger burger = new Burger("맥도날드 슈비버거", 5500, ingredients);

        // 2. 수행
        String json = objectMapper.writeValueAsString(burger);

        // 3. 예상
        String expected = "{\"name\":\"맥도날드 슈비버거\",\"price\":5500,\"ingredients\":[\"통새우 패티\",\"순쇠고기 패티\",\"토마토\",\"어니언 소스\"]}";

        // 4. 검증
        assertEquals(expected, json);
        //System.out.println(json);

        // 4-1. JSON Node활용하기(json을 이쁘게 출력한다)
        JsonNode jsonNode = objectMapper.readTree(json);
        System.out.println(jsonNode.toPrettyString());
    }

    @Test
    public void JSON을_자바_객체로_변환() throws JsonProcessingException {

        // 1. 준비
        ObjectMapper objectMapper = new ObjectMapper();

        //String json = "{\"name\":\"맥도날드 슈비버거\",\"price\":5500,\"ingredients\":[\"통새우 패티\",\"순쇠고기 패티\",\"토마토\",\"어니언 소스\"]}";

        /*
        {
            "name" : "맥도날드 슈비버거",
            "price" : 5500,
            "ingredients" : [ "통새우 패티", "순쇠고기 패티", "토마토", "어니언 소스" ]
        } 와 같은 JSON을 아래코드로 만들 수 있다.
        */
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("name", "맥도날드 슈비버거"); // (key, value)
        objectNode.put("price", 5500); // (key, value)

        ArrayNode arrayNode = objectMapper.createArrayNode(); // createArrayNode() -> []을 만들어줌
        arrayNode.add("통새우 패티");
        arrayNode.add("순쇠고기 패티");
        arrayNode.add("토마토");
        arrayNode.add("어니언 소스");

        objectNode.set("ingredients", arrayNode); //ArrayNode를 넣을때는 put()이 아닌 set()임!

        String json = objectNode.toString();


        // 2. 수행
        //readValue(JSON, JSON으로 만들 클래스 타입을 넣어줌.)
        Burger burger = objectMapper.readValue(json, Burger.class);

        // 3. 예상
        List<String> ingredients = Arrays.asList("통새우 패티", "순쇠고기 패티", "토마토", "어니언 소스");
        Burger expected = new Burger("맥도날드 슈비버거", 5500, ingredients);

        // 4. 검증
        assertEquals(expected.toString(), burger.toString());
        JsonNode jsonNode = objectMapper.readTree(json);
        System.out.println(jsonNode.toPrettyString());
        System.out.println(burger.toString());
    }
}