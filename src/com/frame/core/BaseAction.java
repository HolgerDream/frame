package com.frame.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
    protected Integer curPage;
    public static final long serialVersionUID = 1L;

    public static final String INPUT = "input";

    public static final String JSP = "jsp";

    public static final String URL = "url";

    public static final String JSON = "json";

    public static final String NULLJSON = "nulljson";

    public static final String NOLogin = "noLogin";

    public static final String Login = "login";

    public static final String Error = "error";

    public static final String SUCCESS = "success";

    public static final String FAILURE = "failure";

    public static final String NOFILE = "nofile";

    public static final String DOWNLOAD = "download";

    public static final String PAGE_AJAX = "pages/ajax.jsp";

    public static final String PAGE_RESULT_ALERT = "pages/resultAlert.jsp";

    public static final String PAGE_RESULT_MESSAGE = "pages/resultMessage.jsp";

    public static final String PAGE_NO_FILE = "pages/fileNotExist.jsp";

    public String successResult = "/index.jsp";

    public static final String SHAREURI = "indexAction_sharePage.action,reportManage_viewShare.action,reportManage_fileShare.action";

    /** session 人员 KEY */
    public static final String SESSION_PERSON = "SESSION_PERSON";

    /** session 人员角色 KEY */
    public static final String SESSION_ROLENAMES = "SESSION_ROLENAMES";

    /** context 在线人员 KEY */
    public static final String ONLINE_PERSON = "ONLINE_PERSON";

    /** session 客户端类型 KEY */
    public static final String CLIENT_TYPE = "CLIENT_TYPE";

    /** 数据管理员角色 */
    public static final String DATA_ADMIN_ROLE = "数据管理员角色";

    /** 用户系统参数 */
    public static final String USER_PARAM = "user_param";

    /** 数据控制角色 */
    public static final String DATA_CONTROL_ROLE = "数据控制角色";

    /** pad */
    public static final String MSG = "MSG";

    /** pad */
    public static final String errMsg = "errMsg";

    /** pad */
    public static final String PERSON_ID = "person_id";

    /** pad */
    public static final String Pwd = "pwd";

    /** pad */
    public static final String DATA = "DATA";

    /** pad */
    public static final String AppToken = "app_token";

    /* 是否具有下控权限 */

    public static final String HAS_PHONECTRL_AUTH = "has_phone_control_auth";

    /** pad */
    public static final String LoginURI = "androidLogin_login.action";

    /*
     * pad 全家变量Session
     */
    public final static Map<String, Object> SESSION = new HashMap<String, Object>();

    public static final String UserName = "USER_NAME";

    protected String person_id;

    public String getPerson_id() {
	return person_id;
    }

    public void setPerson_id(String person_id) {
	this.person_id = person_id;
    }

    protected String url = "";

    protected String page = "";

    protected String mdmId = "";

    protected transient final Log logger = LogFactory.getLog(getClass());

    /** 当前是升序还是降序排数据 */
    protected String dir;

    /** 排序的字段 */
    protected String sort;

    /** 每页的大小 */
    protected Integer limit = 25;

    /** 开始取数据的索引号 */
    protected Integer start = 0;

    private Map<String, Object> dataMap = new HashMap<String, Object>();

    private Map<String, Object> NULL = null;

    /**
     * 判断是不是pad传来的参数
     */
    private String pad;

    protected HttpServletRequest getRequest() {
	return ServletActionContext.getRequest();
    }

    protected HttpServletResponse getResponse() {
	return ServletActionContext.getResponse();
    }

    protected HttpSession getSession() {
	return getRequest().getSession();
    }

    @SuppressWarnings("unchecked")
    public static String getTokenForPad(String personMdmId) {
	String token = null;
	Map<String, Object> param = (Map<String, Object>) SESSION
		.get(personMdmId);
	token = (String) param.get(AppToken);
	return token;
    }

    @SuppressWarnings("unchecked")
    public static String getUserNamePad(String personMdmId) {

	Map<String, Object> param = (Map<String, Object>) SESSION
		.get(personMdmId);
	String userName = (String) param.get(UserName);
	return userName;
    }

    /**
     * 
     * 描述：TODO
     * 
     * @param success
     *            如果success 未failure，客户端会弹出对应error字段的信息
     *            如果error为空，不要将success置为failure
     * @param error
     *            错误时显示的错误信息
     * @param data
     */
    protected void setResult(String success, String error,
	    Map<String, Object> data) {
	if (SUCCESS.equals(success)) {
	    this.getDataMap().put(MSG, SUCCESS);
	    this.getDataMap().put(DATA, data);
	} else {
	    this.getDataMap().put(MSG, FAILURE);
	    this.getDataMap().put(errMsg, error);
	}

    }

    // ---------------------------Methods------------------------------
    public String getSuccessResult() {
	return successResult;
    }

    public void setSuccessResult(String successResult) {
	this.successResult = successResult;
    }

    public String list() {
	return SUCCESS;
    }

    public String edit() {
	return INPUT;
    }

    public String save() {
	return INPUT;
    }

    public String delete() {
	return SUCCESS;
    }

    public String multiDelete() {
	return SUCCESS;
    }

    public String multiSave() {
	return SUCCESS;
    }

    public String getDir() {
	return dir;
    }

    public void setDir(String dir) {
	this.dir = dir;
    }

    public String getSort() {
	return sort;
    }

    public void setSort(String sort) {
	this.sort = sort;
    }

    public Integer getLimit() {
	return limit;
    }

    public void setLimit(Integer limit) {
	this.limit = limit;
    }

    public Integer getStart() {
	return start;
    }

    public void setStart(Integer start) {
	this.start = start;
    }

    public String execute() throws Exception {
	HttpServletRequest request = getRequest();
	String uri = request.getRequestURI();
	String url = uri.substring(request.getContextPath().length());
	url = url.replace(".do", ".jsp");
	url = "/pages" + url;

	if (logger.isInfoEnabled()) {
	    logger.info("forward url:" + url);
	}
	return SUCCESS;

    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    /**
     * 描述：重定向设置Url
     * 
     * @param url
     *            跳转页面
     * @param isCrossProject
     *            是否跨项目
     */
    public void setUrl(String url, boolean isCrossProject) {
	if (isCrossProject) {
	    String basePath = getRequest().getScheme() + "://"
		    + getRequest().getServerName() + ":"
		    + getRequest().getServerPort();
	    this.url = basePath + url;
	} else {
	    setUrl(url);
	}
    }

    public String getPage() {
	return page;
    }

    public void setPage(String page) {
	this.page = page;
    }

    public String getMdmId() {
	return mdmId;
    }

    public void setMdmId(String mdmId) {
	this.mdmId = mdmId;
    }

    public String getBasePath() {
	return getRequest().getScheme() + "://" + getRequest().getServerName()
		+ ":" + getRequest().getServerPort()
		+ getRequest().getContextPath() + "/";
    }

    public Map<String, Object> getDataMap() {
	return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
	this.dataMap = dataMap;
    }

    /**
     * 获取pad
     */
    public String getPad() {
	return pad;
    }

    /**
     * 设置pad
     */
    public void setPad(String pad) {
	this.pad = pad;
    }

    // 测试post开放跨域，正式发布不允许跨域
    public void postTest() {
	HttpServletResponse response = ServletActionContext.getResponse();
	response.setHeader("Access-Control-Allow-Origin", "*");
    }

    public Integer getCurPage() {
	return curPage;
    }

    public void setCurPage(Integer curPage) {
	this.curPage = curPage;
    }

    public Map<String, Object> getNULL() {
	return NULL;
    }

    public void setNULL(Map<String, Object> nULL) {
	NULL = nULL;
    }

    public static String getUUID() {
	String s = UUID.randomUUID().toString();
	return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
		+ s.substring(19, 23) + s.substring(24);
    }

}
