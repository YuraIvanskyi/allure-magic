import pytest


@pytest.fixture(autouse=True)
def fixture1():
    print(1)
    yield


@pytest.fixture(autouse=True)
def fixture2():
    print(1)
    yield


@pytest.fixture(autouse=True)
def fixture3():
    yield
    print(1)


@pytest.fixture(autouse=True)
def fixture4():
    yield
    print(1)
