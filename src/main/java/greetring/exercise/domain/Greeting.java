package greetring.exercise.domain;

public class Greeting
{
    private final long id;
    private final String content;

    public Greeting(long id, String content)
    {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Greeting)
        {
            return this.id == ((Greeting) obj).id;
        }
        else
        {
            return false;
        }
    }
}
