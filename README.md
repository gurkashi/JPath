# JPath
XPath for json objects

Usages:

    @Test
    public void put() throws JPathException {
        JPath json = new JPath();

        json.put(".employees", new JSONArray());

        json.put(".employees[0]", new JSONObject());
        json.put(".employees[1]", new JSONObject());
        json.put(".employees[2]", new JSONObject());

        json.put(".employees[0].firstName", "John");
        json.put(".employees[0].lastName", "Doe");
        json.put(".employees[0].age", 15);
        json.put(".employees[0].adult", false);

        json.put(".employees[1].firstName", "Anna");
        json.put(".employees[1].lastName", "Smith");
        json.put(".employees[1].age", 25);
        json.put(".employees[1].adult", true);

        json.put(".employees[2].firstName", "Peter");
        json.put(".employees[2].lastName", "Jones");
        json.put(".employees[2].age", 19);
        json.put(".employees[2].adult", true);

        String expected = new JSONObject(jsonString).toString();
        String actual = json.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get() throws JPathException {
        JPath json = new JPath(jsonString);

        String textPath = ".employees[1].lastName";
        String intPath = ".employees[2].age";
        String boolPath = ".employees[0].adult";

        String textResult = (String) json.get(textPath);
        int intResult = (Integer) json.get(intPath);
        boolean boolResult = (Boolean) json.get(boolPath);

        Assert.assertEquals("Smith", textResult);
        Assert.assertEquals(19, intResult);
        Assert.assertEquals(false, boolResult);
    }

    @Test
    public void arrays() throws JPathException {
        String json = "{\"data\":[[[[{\"name\":\"gur\"}]]]]}";

        JPath jpath = new JPath(json);

        Assert.assertEquals("gur", jpath.get(".data[0][0][0][0].name"));
    }

    @Test
    public void removeKey() throws JPathException {
        JPath json = new JPath(jsonString);

        json.remove(".employees[1].lastName");

        try {
            json.get(".employees[1].lastName");
        }
        catch (JSONException keyNotFound){}
    }

    @Test
    public void removeIndex() throws JPathException {
        JPath json = new JPath(jsonString);

        String name1 = (String) json.get(".employees[2].firstName");
        json.remove(".employees[1]");
        String name2 = (String) json.get(".employees[1].firstName");

        Assert.assertEquals(name1, name2);
    }

    @Test
    public void rename() throws JPathException {
        JPath json = new JPath(jsonString);

        String before = (String) json.get(".employees[2].firstName");
        json.rename(".employees[2].firstName", "name");
        String after = (String) json.get(".employees[2].name");

        Assert.assertEquals(before, after);
        try {
            json.get(".employees[2].firstName");
        }
        catch (JSONException keyNotFound){}
    }

    @Test
    public void edit() throws JPathException {
        JPath json = new JPath(jsonString);

        json.put(".employees[2].firstName", "me");
    }

    @Test
    public void size() throws JPathException {
        JPath json = new JPath(jsonString);

        Assert.assertEquals("array size failed", 3, json.size(".employees"));
        Assert.assertEquals("map size failed", 4, json.size(".employees[0]"));
        try{
            json.size(".employees[0].firstName");
        }
        catch (JPathException ex){
            Assert.assertEquals("Object is not a valid json container", ex.getMessage());
        }
    }

    @Test
    @Ignore
    public void equals(){
        JPath j1 = new JPath("{\"first_name\":\"gur first name\",\"phone\":\"1234567890\",\"address\":{\"street\":\"abcd\",\"country\":\"efgh\"},\"email\":\"gur@wishbox.co\",\"last_name\":\"kashi bla bla\",\"creditcard\":{\"Expiration\":\"0518\",\"CreditNum\":\"4580000000000000\",\"ccv\":\"123\"},\"udid\":\"23d5286775b894c5\",\"passport\":\"000000000\",\"password\":\"aaaa\"}\n");
        JPath j2 = new JPath("{\n" +
                "\t\"first_name\":\"gur first name\",\n" +
                "\t\"phone\":\"1234567890\",\n" +
                "\t\"address\":{\n" +
                "\t\t\"street\":\"abcd\",\n" +
                "\t\t\"country\":\"efgh\"\n" +
                "\t\t},\n" +
                "\t\"email\":\"gur@wishbox.co\",\n" +
                "\t\"last_name\":\"kashi bla bla\",\n" +
                "\t\"password\":\"aaaa\",\n" +
                "\t\"udid\":\"23d5286775b894c5\",\n" +
                "\t\"passport\":\"000000000\",\n" +
                "\t\"creditcard\":{\n" +
                "\t\t\"CreditNum\":\"4580000000000000\",\n" +
                "\t\t\"Expiration\":\"0518\",\n" +
                "\t\t\"ccv\":\"123\"\n" +
                "\t\t}\n" +
                "}");

        System.out.println(j1);
        System.out.println(j2);

        Assert.assertEquals(j1, j2);

        return;
    }

}
