import allure, pytest


@pytest.mark.parametrize("test_input,expected", [(1, 1), (1, 2), (1, 1)])
@allure.title("Magic good test")
def test_magic(test_input, expected):
    assert test_input == expected
    magic_step()
    magic_step()
    allure.attach(body="adsadasdasdadad")


@allure.title("Magic evil test")
def test_magic_evil():
    assert 1 == 1
    magic_step()
    magic_step()
    assert 1 == 2


@allure.title("Magic broken test")
def test_magic_broken(info):
    assert 1 == 1
    magic_step()
    magic_step()
    assert 1 == 2 / 0


@allure.step("Magic step")
def magic_step():
    assert 1 == 1
    magic_spell()
    magic_spell()


@allure.step("Magic spell")
def magic_spell():
    assert 1 == 1
