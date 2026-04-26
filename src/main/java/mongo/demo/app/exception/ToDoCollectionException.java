package mongo.demo.app.exception;


public class ToDoCollectionException extends Exception{

    public ToDoCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return "Todo not found with id: " + id;
    }

    public static String ToDoAlreadyExistsException(String name) {
        return "Todo already exists with name " + name;
    }
}
