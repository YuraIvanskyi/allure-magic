import allure


@allure.title("Magic good test")
def test_magic():
    assert 1 == 1
    magic_step()
    magic_step()


@allure.title("Magic evil test")
def test_magic_evil():
    assert 1 == 1
    magic_step()
    magic_step()
    assert 1 == 2


@allure.step("Magic step")
def magic_step():
    assert 1 == 1
    magic_spell()
    magic_spell()


@allure.step("Magic spell")
def magic_spell():
    assert 1 == 1
