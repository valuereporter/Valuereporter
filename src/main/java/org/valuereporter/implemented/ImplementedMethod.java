package org.valuereporter.implemented;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public class ImplementedMethod {
    private String prefix = "";
    private final String name;


    public ImplementedMethod(String name) {
        this.name = name;
        prefix = "not-set";
    }
    public ImplementedMethod(String prefix, String name) {
        this.prefix = prefix;
        this.name = name;
    }



    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }


    @Override
    public String toString() {
        return "ObservedMethod{" +
                "name='" + name +
                ", prefix='" + prefix + '\'' +
                '}';
    }

    public String toCsv() {
        return new String(getPrefix() +"," + getName());

    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
