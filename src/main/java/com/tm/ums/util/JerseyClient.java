package com.tm.ums.util;

import com.tm.ums.filters.CheckRequestFilter;
import com.tm.ums.interceptors.GZIPReaderWriterInterceptor;
import java.io.InputStream;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;



import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.MultiPart;

/**
 * Jersey client class to post restful client calls in variety of ways.
 *
 * @version 1.0
 */
public class JerseyClient {

    private Client client = null;
    private WebTarget webResource;
//    private final static int timeout = 10000;
    private final String BASE_URI;

    /**
     * Connect with any repository
     *
     * @param host
     * * @param port
     * * @param context
     */
    public JerseyClient(String as_Host, String as_Port, String as_Context) {
        client = this.client = ClientBuilder.newClient();
        this.client.register(GZIPReaderWriterInterceptor.class);
        
        // System.out.println(properties.getProperty("repositoryServer"));
        BASE_URI = "http://"+ as_Host + ":"
                + as_Port + as_Context;
        this.webResource = this.client.target(BASE_URI);
        this.webResource.register(CheckRequestFilter.class);
        
    }

    /**
     * connect with specified repository url
     *
     * @param as_Url
     */
    public JerseyClient(String as_Url) {
        client = this.client = ClientBuilder.newClient();
        this.client.register(GZIPReaderWriterInterceptor.class);
        BASE_URI = as_Url;
        this.webResource = this.client.target(BASE_URI);
        this.webResource.register(CheckRequestFilter.class);
    }

    /**
     * to send the GET binary request by specified url
     *
     * @param path
     * @param map containing the required request parameters
     * @return response input stream
     */
    public InputStream sendGetBinaryRequestByUrl(String path, Map<String, String> param) {
        InputStream responseXml = null;        
        try {
            if (param != null) {
                responseXml = fillQueryParams(param).path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML).get(InputStream.class);
            } else {
                responseXml = webResource.path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML).get(InputStream.class);
            }
        } catch (Exception exp) {
//            logger.msg(Constants.LEVEL_ERROR, exp.getMessage(), LogUtil.getLineNumber());
            exp.printStackTrace();
        }

        return responseXml;
    }

    /**
     * to send the simple GET request
     *
     * @param path
     * @param map containing the required request parameters
     * @return response string
     */
    public String sendGetRequest(String path, Map<String, String> param) {
        String responseXml = null;

        try {
            if (param != null) {
                responseXml = fillQueryParams(param).path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML).get(String.class);
            } else {
                responseXml = webResource.path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML).get(String.class);
            }
        } catch (Exception exp) {
//            logger.msg(Constants.LEVEL_ERROR, exp.getMessage(), LogUtil.getLineNumber());
            exp.printStackTrace();
        }

        return responseXml;
    }

    /**
     * to send the simple DELETE request
     *
     * @param path
     * @param map containing the required request parameters
     * @return response string
     */
    public String sendDeleteRequest(String path, Map<String, String> param) {
        String responseXml = null;

        try {
            if (param != null) {
                responseXml = fillQueryParams(param).path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML).delete(String.class);
            } else {
                responseXml = webResource.path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML).delete(String.class);
            }
        } catch (Exception exp) {
//            logger.msg(Constants.LEVEL_ERROR, exp.getMessage(), LogUtil.getLineNumber());
            exp.printStackTrace();
        }

        return responseXml;
    }

    /**
     * to send the GET binary request
     *
     * @param path
     * @param map containing the required request parameters
     * @return response input stream
     */
    public InputStream sendGetBinaryRequest(String path, Map<String, String> param) {
        InputStream responseXml = null;

        try {
            if (param != null) {
                responseXml = fillQueryParams(param).path(path).request(MediaType.APPLICATION_OCTET_STREAM).get(InputStream.class);
            } else {
                responseXml = webResource.path(path).request(MediaType.APPLICATION_OCTET_STREAM).get(InputStream.class);
            }
        } catch (Exception exp) {
//            logger.msg(Constants.LEVEL_ERROR, exp.getMessage(), LogUtil.getLineNumber());
            exp.printStackTrace();
        }

        return responseXml;
    }

    /**
     * to send the POST request
     *
     * @param path
     * @param formParams
     * @return response string
     */
    public String sendPostRequestWithFormParams(String path, MultivaluedMap<String, String> formParams) {
        return sendPostRequest(path, formParams, null);
    }

    /**
     * to send the POST request
     *
     * @param path
     * @param formParams
     * @param queryParams
     * @return response string
     */
    public String sendPostRequest(String path,
            MultivaluedMap<String, String> formParams,
            MultivaluedMap<String, String> queryParams) {
        String responseXml = null;

        try {            
            if (formParams != null && queryParams != null) {
                responseXml = fillQueryParams(queryParams).path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML).post(Entity.form(formParams), String.class);
            } else if (formParams != null) {
                responseXml = webResource.path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML).post(Entity.form(formParams), String.class);
            } else {
                throw new RuntimeException("Bad request: Post data not provided.");
            }

        } catch (Exception exp) {
//            logger.msg(Constants.LEVEL_ERROR, exp.getMessage(), LogUtil.getLineNumber());
            exp.printStackTrace();
        }

        return responseXml;
    }
    
    private WebTarget fillQueryParams(Map<String, ?> queryParams) {
        for (Map.Entry<String, ?> en : queryParams.entrySet()) {
            webResource.queryParam(en.getKey(), en.getValue());
        }
        return webResource;
    }

    /**
     * to send the POST request
     *
     * @param path
     * @param xml
     * @return response string
     */
    public String sendPostRequestXmlData(String path, String xml) {
        String responseXml = null;

        try {
            if (xml != null) {
                responseXml = webResource.path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML).post(Entity.<String>xml(xml), String.class);
            } else {
                throw new RuntimeException("Bad request: Post data not provided.");
            }
        } catch (Exception exp) {
//            logger.msg(Constants.LEVEL_ERROR, exp.getMessage(), LogUtil.getLineNumber());
            exp.printStackTrace();
        }

        return responseXml;
    }
    
    /**
     * to send the POST request
     *
     * @param path
     * @param json
     * @return response string
     */
    public String sendPostRequestJsonData(String path, String json) {
        String responseXml = null;

        try {
            if (json != null) {
                responseXml = webResource.path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON).post(Entity.<String>json(json), String.class);
            } else {
                throw new RuntimeException("Bad request: Post data not provided.");
            }
        } catch (Exception exp) {
//            logger.msg(Constants.LEVEL_ERROR, exp.getMessage(), LogUtil.getLineNumber());
            exp.printStackTrace();
        }

        return responseXml;
    }

    /**
     * to send the PUT request
     *
     * @param path relative path where data will be sent
     * @param xml Object in xml which we want to send in HTTP PUT method
     * @return response string
     */
    public String sendPutRequestXmlData(String path, String xml) {
        String responseXml = null;

        try {
            if (xml != null) {
                responseXml = webResource.path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML).put(Entity.<String>xml(xml), String.class);
            } else {
                throw new RuntimeException("Bad request: PUT data not provided.");
            }
        } catch (Exception exp) {
//            logger.msg(Constants.LEVEL_ERROR, exp.getMessage(), LogUtil.getLineNumber());
            exp.printStackTrace();
        }

        return responseXml;
    }
    
    /**
     * to send the PUT request
     *
     * @param path relative path where data will be sent
     * @param json Object in xml which we want to send in HTTP PUT method
     * @return response string
     */
    public String sendPutRequestJsonData(String path, String json) {
        String responseXml = null;

        try {
            if (json != null) {
                responseXml = webResource.path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML).put(Entity.<String>json(json), String.class);
            } else {
                throw new RuntimeException("Bad request: PUT data not provided.");
            }
        } catch (Exception exp) {
//            logger.msg(Constants.LEVEL_ERROR, exp.getMessage(), LogUtil.getLineNumber());
            exp.printStackTrace();
        }

        return responseXml;
    }

    /**
     * to send the multipart POST request
     *
     * @param path
     * @param param
     * @param form
     * @return response string
     */
    public String sendPostMultiPartRequest(String path, MultivaluedMap<String, String> param, MultiPart form) {
        String response = null;
        try {
            if (param != null) {
                response = fillQueryParams(param).path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML).post(Entity.entity(form, form.getMediaType()), String.class);
            } else {
                response = webResource.path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML).post(Entity.entity(form, form.getMediaType()), String.class);
            }
        } catch (Exception exp) {
//            logger.msg(Constants.LEVEL_ERROR, exp.getMessage(), LogUtil.getLineNumber());
            exp.printStackTrace();
        }

        return response;
    }

    /**
     * to send the POST request
     *
     * @param path
     * @param param
     * @return reponse input stream
     */
    public InputStream sendPostRequest_InputStream(String path, MultivaluedMap<String, String> param) {
        InputStream responseXml = null;

        try {
            if (param != null) {
                responseXml = fillQueryParams(param).path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML).get(InputStream.class);
            } else {
                responseXml = webResource.path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML).get(InputStream.class);
            }
        } catch (Exception exp) {
//            logger.msg(Constants.LEVEL_ERROR, exp.getMessage(), LogUtil.getLineNumber());
            exp.printStackTrace();
        }

        return responseXml;
    }

    /**
     * to send the simple GET request
     *
     * @param path
     * @param map containing the required request parameters
     * @return response string
     */
    public String sendGetRequest(String path) {
        String responseXml = null;

        try {
            responseXml = webResource.path(path).request(MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.TEXT_HTML).get(String.class);
        } catch (Exception exp) {
//            logger.msg(Constants.LEVEL_ERROR, exp.getMessage(), LogUtil.getLineNumber());
            exp.printStackTrace();
        }
        System.out.println("send get request executed");
        System.out.println(responseXml);
        return responseXml;
    }
    
    public WebTarget getWebResource() {
        return webResource;
    }

}
