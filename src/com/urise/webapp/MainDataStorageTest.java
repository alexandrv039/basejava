package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.PathStorage;
import com.urise.webapp.storage.serializer.DataStreamSerializer;

public class MainDataStorageTest {
    public static void main(String[] args) {
        PathStorage pathStorage = new PathStorage("/Users/aleksandrvinokurov/Documents/Projects/basejava/storage",
                new DataStreamSerializer());
        Resume r1 = ResumeTestData.getNewResume("uuid1", "Alexandr");
        pathStorage.save(r1);

        Resume r2 = pathStorage.get(r1.getUuid());
        System.out.println(r1.equals(r2));

        double startPrice = 100;
        Context context = new Context(new HalfPrice());
        double price = context.getPrice(startPrice);
        System.out.println(price);
    }
}

interface Strategy{
    double getPrice(double price);
}

class FullPrice implements Strategy {
    @Override
    public double getPrice(double price) {
        return price;
    }
}

class HalfPrice implements Strategy {
    @Override
    public double getPrice(double price) {
        return price * 0.5;
    }
}

class Context{
    Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public double getPrice(double price) {
        return strategy.getPrice(price);
    }
}
