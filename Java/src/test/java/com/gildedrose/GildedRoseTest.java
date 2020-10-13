package com.gildedrose;



import org.hamcrest.core.AllOf;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQualityRefactored();
        assertEquals("foo", app.items[0].name);
    }

    // can be done better
    @Test
    void sellInShouldDecrease() {
        Item[] items = new Item[] { new Item("item1", 4, 10),new Item("item2", 4, 10) };
        GildedRose app = new GildedRose(items);

        int days = 3;
        for (int i=0; i<days; i++) {
            app.updateQualityRefactored();
        }

        for (int i =0; i<items.length; i++) {
            assertThat(String.format("Wrong sellIn for item %d",i),items[i].sellIn,lessThan(4));
        }
    }

    @Test
    void qualityShouldDecrease() {
        Item[] items = new Item[] { new Item("item1", 2, 10),new Item("item2", 4, 10) };
        GildedRose app = new GildedRose(items);

        int days = 3;
        for (int i=0; i<days; i++) {
            app.updateQualityRefactored();
        }

        for (int i =0; i<items.length; i++) {
            assertThat(String.format("Wrong quality for item %d",i),items[i].quality,lessThan(10));
        }
    }

    @Test
    void qualityDeadlineShouldDecreaseTwiceFast() {
        Item[] items = new Item[] { new Item("item1", 0, 10),new Item("item1", 0, 10) };
        GildedRose app = new GildedRose(items);

        int days = 3;
        for (int i=0; i<days; i++) {
            app.updateQualityRefactored();
        }

        for (int i =0; i<items.length; i++) {
            assertThat(String.format("Wrong quality for item %d",i),items[i].quality,lessThan(5));
        }
    }

    @Test
    void qualityShouldNotBecomeNegative() {
        Item[] items = new Item[] { new Item("foo", 2, 0) };
        GildedRose app = new GildedRose(items);

        int days = 6;
        for (int i=0; i<days; i++) {
            app.updateQualityRefactored();
        }

        for (int i =0; i<items.length; i++) {
            assertThat(String.format("Wrong quality for item %d",i),items[i].quality,greaterThanOrEqualTo(0));
        }
    }

    @Test
    void specialItemsQualityShouldIncrease() {
        Item[] items = new Item[] { new Item("Aged Brie", 10, 0),
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 0)};
        GildedRose app = new GildedRose(items);

        int days = 3;
        for (int i=0; i<days; i++) {
            app.updateQualityRefactored();
        }

        for (int i =0; i<items.length; i++) {
            assertThat(String.format("Wrong quality for item %d",i),items[i].quality,greaterThan(0));
        }
    }

    @Test
    void specialItemsQualityShouldNotExceed50() {
        Item[] items = new Item[] { new Item("Aged Brie", 60, 40),
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 40) };
        GildedRose app = new GildedRose(items);

        int days = 10;
        for (int i=0; i<days; i++) {
            app.updateQualityRefactored();
        }

        for (int i =0; i<items.length; i++) {
            assertThat(String.format("Wrong quality for item %d",i),items[i].quality, allOf(greaterThan(0),equalTo(50)));
        }
    }

    @Test
    void legendaryItemsQualityNeverAlters() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 10, 80) };
        GildedRose app = new GildedRose(items);

        int days = 70;
        for (int i=0; i<days; i++) {
            app.updateQualityRefactored();
        }

        for (int i =0; i<items.length; i++) {
            assertThat(String.format("Wrong quality for item %d",i),items[i].quality,greaterThan(50));
        }
    }


    @Test
    void conjuredItemsQualityShouldDecreaseTwiceFast() {
        Item[] items = new Item[] { new Item("Conjured", 10, 4) };
        GildedRose app = new GildedRose(items);

        int days = 2;
        for (int i=0; i<days; i++) {
            app.updateQualityRefactored();
        }

        for (int i =0; i<items.length; i++) {
            assertThat(String.format("Wrong quality for item %d",i),items[i].quality,equalTo(0));
        }
    }



}