package com.gildedrose;

class GildedRose {
    static final String AGED_BRIE = "Aged Brie";
    static final String PASSES = "Backstage passes to a TAFKAL80ETC concert";
    static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    static final String CONJURED = "Conjured";

    static final int DEADLINE = 0;
    static final int ZERO_QUALITY = 0;
    static final int MAX_QUALITY = 50;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQualityRefactored() {
        for (int i = 0; i < items.length; i++) {
            String itemName = items[i].name;
            if (itemName != null && !itemName.equals(SULFURAS)) {
                boolean conjured = itemName.equals(CONJURED);
                if (!conjured) {
                    boolean itemSpecial = itemName.equals(AGED_BRIE) || itemName.equals(PASSES);
                    if (!itemSpecial) {
                        items[i] = updateNormal(items[i]);
                    } else {
                        items[i] = updateSpecial(items[i]);
                    }
                } else {
                    items[i] = updateConjured(items[i]);
                }
            }
        }
    }

    private Item updateNormal(Item normalItem) {
        normalItem.quality = decrementAndGetQuality(normalItem.quality);
        normalItem.sellIn--;

        if (normalItem.sellIn < DEADLINE) {
            normalItem.quality = decrementAndGetQuality(normalItem.quality);
        }
        return normalItem;
    }

    private Item updateSpecial(Item specialItem) {
        specialItem.quality = incrementAndGetQuality(specialItem.quality);
        specialItem.sellIn--;

        switch (specialItem.name) {
            case AGED_BRIE:
                if (specialItem.sellIn < DEADLINE)
                    specialItem.quality = incrementAndGetQuality(specialItem.quality);
                break;
            case PASSES:
                if (specialItem.sellIn < 11)
                    specialItem.quality = incrementAndGetQuality(specialItem.quality);
                if (specialItem.sellIn < 6)
                    specialItem.quality = incrementAndGetQuality(specialItem.quality);
                if (specialItem.sellIn < DEADLINE)
                    specialItem.quality = specialItem.quality - specialItem.quality;
                break;
            default:
                System.out.println("[Warning] This should not happen");
        }

        return specialItem;
    }


    private Item updateConjured(Item conjuredItem) {
        conjuredItem.quality = decrementAndGetQuality(conjuredItem.quality);
        conjuredItem.quality = decrementAndGetQuality(conjuredItem.quality);
        conjuredItem.sellIn--;

        if (conjuredItem.sellIn < DEADLINE) {
            conjuredItem.quality = decrementAndGetQuality(conjuredItem.quality);
            conjuredItem.quality = decrementAndGetQuality(conjuredItem.quality);
        }
        return conjuredItem;
    }


    private int incrementAndGetQuality(int quality) {
        if (quality < MAX_QUALITY)
            quality++;
        return quality;
    }

    private int decrementAndGetQuality(int quality) {
        if (quality > ZERO_QUALITY)
            quality--;
        return quality;
    }


    // This is the method I needed to refactor
    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            if (!items[i].name.equals("Aged Brie")
                    && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (items[i].quality > 0) {
                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } else {
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1;

                    if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < 0) {
                if (!items[i].name.equals("Aged Brie")) {
                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                                items[i].quality = items[i].quality - 1;
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
        }
    }
}