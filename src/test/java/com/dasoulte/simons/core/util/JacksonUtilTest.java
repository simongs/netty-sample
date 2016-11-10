package com.dasoulte.simons.core.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by NHNEnt on 2016-11-10.
 */
public class JacksonUtilTest {
    @Test
    public void test() {
        JacksonElement element = makeElement();

        String json = JacksonUtil.toJson(element);
        JacksonElement actual = JacksonUtil.toObject(json, JacksonElement.class);

        assertEquals(element.getEl1(), actual.getEl1());
        assertEquals(element.getEl2(), actual.getEl2());
        assertEquals(element.getEl3(), actual.getEl3());
        assertEquals(element.getEl4(), actual.getEl4());
    }

    @Test
    public void testToPrettyJson() {
        JacksonElement element = makeElement();
        String result = JacksonUtil.toPrettyJson(element);
        assertNotNull(result);
        assertTrue(StringUtils.contains(result, "\n"));
    }

    private JacksonElement makeElement() {
        List<ItemElement> itemList = Lists.newArrayList();
        ItemElement item = new ItemElement();
        item.setName("test name");
        item.setNum(33);
        itemList.add(item);

        JacksonElement element = new JacksonElement();
        element.setEl1("testElement");
        element.setEl2(8);
        element.setEl3(8D);
        element.setEl4(new Date());

        element.setItemList(itemList);
        return element;
    }

    private static class JacksonElement {
        private String el1;
        private int el2;
        private Double el3;
        private Date el4;
        private List<ItemElement> itemList;

        public String getEl1() {
            return el1;
        }

        public void setEl1(String el1) {
            this.el1 = el1;
        }

        public int getEl2() {
            return el2;
        }

        public void setEl2(int el2) {
            this.el2 = el2;
        }

        public Double getEl3() {
            return el3;
        }

        public void setEl3(Double el3) {
            this.el3 = el3;
        }

        public Date getEl4() {
            return el4;
        }

        public void setEl4(Date el4) {
            this.el4 = el4;
        }

        public List<ItemElement> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemElement> itemList) {
            this.itemList = itemList;
        }
    }

    private static class ItemElement {
        private String name;
        private int num;

        public ItemElement() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}