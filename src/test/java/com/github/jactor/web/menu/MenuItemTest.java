package com.github.jactor.web.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("A MenuItem")
class MenuItemTest {

  private static final String HIT_DEAD_CENTER = "hit?dead=center";

  @Test
  @DisplayName("should have an implementation of the hash code method")
  void willHaveCorrectImplementedHashCode() {
    MenuItem base = new MenuItem(
        "an item", "target?some=parameter"
    ).addChild(new MenuItem("a child", "childTarget?child=parameter"));
    MenuItem equal = new MenuItem(
        "an item", "target?some=parameter"
    ).addChild(new MenuItem("a child", "childTarget?child=parameter"));
    MenuItem notEqual = new MenuItem("another item", "target?another=parameter");

    assertAll(
        () -> assertThat(base.hashCode()).as("(%s).hashCode() is equal to (%s).hashCode()", base, equal).isEqualTo(equal.hashCode()),
        () -> assertThat(base.hashCode()).as("(%s).hashCode() is not equal to (%s).hashCode()", base, notEqual).isNotEqualTo(notEqual.hashCode()),
        () -> assertThat(base.hashCode()).as("(%s).hashCode() is a number with different value", base).isNotEqualTo(0)
    );
  }

  @Test
  @DisplayName("should have an implementation of the equals method")
  void willHaveCorrectImplementedEquals() {
    MenuItem base = new MenuItem(
        "an item", "target?some=parameter"
    ).addChild(new MenuItem("a child", "childTarget?child=parameter"));
    MenuItem equal = new MenuItem(
        "an item", "target?some=parameter"
    ).addChild(new MenuItem("a child", "childTarget?child=parameter"));
    MenuItem notEqual = new MenuItem("another item", "target?another=parameter");

    assertAll(
        () -> assertThat(base).as("%s is equal to %s", base, equal).isEqualTo(equal),
        () -> assertThat(base).as("%s is not equal to %s", base, notEqual).isNotEqualTo(notEqual),
        () -> assertThat(base).as("%s is not equal to %s").isNotEqualTo(null),
        () -> assertThat(base).as("%s is equal to %s").isEqualTo(base),
        () -> assertThat(base).as("base is not same instance as equal").isNotSameAs(equal)
    );
  }

  @Test
  @DisplayName("should not be chosen when the target is unknown")
  void shouldNotBeChosenWhenTheTargetIsUnknown() {
    MenuItem testMenuItem = new MenuItem("an item", "miss");
    assertThat(testMenuItem.isChosen(HIT_DEAD_CENTER)).isEqualTo(false);
  }

  @Test
  @DisplayName("should be chosen when the target is known")
  void shouldBeChosenWhenTheTargetIsKnown() {
    MenuItem testMenuItem = new MenuItem("an item", HIT_DEAD_CENTER);
    assertThat(testMenuItem.isChosen(HIT_DEAD_CENTER)).isEqualTo(true);
  }

  @Test
  @DisplayName("should not have chosen child when the target is unknown")
  void shouldNotBeChosenChildWhenMenuTargetOnChildIsUnknown() {
    MenuItem testMenuItem = new MenuItem("an item", HIT_DEAD_CENTER)
        .addChild(new MenuItem("a child", "miss"));

    assertThat(testMenuItem.isChildChosen(HIT_DEAD_CENTER)).isEqualTo(false);
  }

  @Test
  @DisplayName("should have chosen child when the target is known")
  void shouldBeChosenChildWhenMenuTargetOnChildIsKnown() {
    MenuItem testMenuItem = new MenuItem("an item", "miss")
        .addChild(new MenuItem("an item", HIT_DEAD_CENTER));

    assertThat(testMenuItem.isChildChosen(HIT_DEAD_CENTER)).isEqualTo(true);
  }
}
