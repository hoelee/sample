package com.hoelee.demo.demo.exception;

/**
 * <p>Class desc:</p>
 * Handle own JSONObject & JSONArray convert to object
 * 
 * @version        v2, 2020-10-01 06:39:32PM
 * @author         hoelee
 */
public class ExceptionJSONConversion extends Exception {

    private Class modelClass;

    /** hoelee v2 2020-10-01 06:39:32PM
     * <p>Constructor desc:</p>
     * 
     * 
     * @param message 
     */
    public ExceptionJSONConversion(String message) {
        super(message);

        this.modelClass = modelClass;
    }

    /** hoelee v2 2020-10-01 06:39:32PM
     * <p>Method desc:</p>
     * 
     * @return 
     */
    public Class getModelClass() {
        return modelClass;
    }
}

//~ v2, 2020-10-01 06:39:32PM - Last edited by hoelee
