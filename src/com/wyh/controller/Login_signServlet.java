package com.wyh.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wyh.Levenshtein.Levenshtein;
import com.wyh.SearchCache.ArrayListCache;
import com.wyh.bean.ArticleBean;
import com.wyh.bean.SearchBean;
import com.wyh.bean.User;
import com.wyh.service.ArticleService;
import com.wyh.service.UserManagerService;

import email.SendEmail;


@Controller
public class Login_signServlet {

	Logger log = Logger.getLogger(Login_signServlet.class);
	
	@Autowired
	private UserManagerService userManagerService = null;
	
	@Autowired
	private ArticleService articleService = null;

	
	@RequestMapping(value="/search.action",method=RequestMethod.POST)
	public String search(@RequestParam String search,Model model ) throws Exception
	{
		List<SearchBean> searchBeans = null;
		List<ArticleBean> articleBeans = null;
		String target = "";
		articleBeans = articleService.getAllArticle();
		for (ArticleBean articleBean : articleBeans) {
			target = articleBean.getArticle_name();
			float similarityRation = Levenshtein.getSimilarityRatio(search, target);

			if (similarityRation != 0.0f) {
				if (searchBeans == null) {
					searchBeans = new ArrayList<SearchBean>(100);
				}
				SearchBean searchBean = new SearchBean();
				searchBean.setArticleBean(articleBean);
				searchBean.setSimilarityRatio(similarityRation);
				searchBeans.add(searchBean);
			}
		}
		if (searchBeans != null && searchBeans.size() > 0) {
			Collections.sort(searchBeans, new Comparator<SearchBean>() {
				@Override
				public int compare(SearchBean o1, SearchBean o2) {
					if (o1.getSimilarityRatio() <= o2.getSimilarityRatio()) {
						return 1;
					}
					return -1;
				}
			});
			searchBeans = ArrayListCache.add(searchBeans);

			String eStr = URLEncoder.encode(search, "utf-8");
			
			return "redirect:" + "searchPage.action?page=1&&search=" + eStr;
		} else {
			model.addAttribute("search", search);
			return "/WEB-INF/view/All_article_list.jsp";
		}
		
	}

	@RequestMapping(value = "/searchPage.action", method = RequestMethod.GET)
	public String SearchPage(@RequestParam String search,
			@RequestParam(required=false) Integer page,//TODO：默认是？？?
			Model model) {
		// TODO:搜索分页
		List<SearchBean> searchBeans = ArrayListCache.getArrayList();
		List<ArticleBean> articleBeans = new ArrayList<>(100);
		int sumCount = searchBeans.size();
		model.addAttribute("search", search);
		if (sumCount > 0) {
			int sumPage = ((sumCount % 10) > 0) ? ((sumCount / 10) + 1) : (sumCount / 10);
			if (page == null) {
				page = 1;
			}
			for (int i = 10 * (page - 1); i < (((sumCount % 10) > 0) ? sumCount : page * 10); i++) {
				articleBeans.add(searchBeans.get(i).getArticleBean());
			}
			if (page + 10 <= sumPage) {
				model.addAttribute("end", page + 10);
			} else {
				model.addAttribute("end", sumPage);
			}
			model.addAttribute("sumPage", sumPage);
			model.addAttribute("currentPage", page);
			model.addAttribute("articleList", articleBeans);
			model.addAttribute("action", "search");
			return "/WEB-INF/view/All_article_list.jsp";
		}
		else {
			return "redirect:"+"Error.action";
		}
	}
	
	@RequestMapping(value="/article_page_By_search.action",method = RequestMethod.GET)
	public String article_page_By_search(@RequestParam(required=false) String id,Model model,
			HttpServletRequest req){

		List<SearchBean> searchBeans = ArrayListCache.getArrayList();
		ArticleBean articleBean = new ArticleBean();
		if (searchBeans.size() > 0) {
			int i = 0;
			do {
				if (i == 0 && searchBeans.size() > 1) {
					searchBeans.get(i).getArticleBean()
							.setNext_id(searchBeans.get(i + 1).getArticleBean().getArticle_id());
					searchBeans.get(i).getArticleBean()
							.setNext_name(searchBeans.get(i + 1).getArticleBean().getArticle_name());
				} 
				else if (i==0&&searchBeans.size()==1) {
					articleBean = searchBeans.get(i).getArticleBean();
					i++;
					break;
				} 
				else if (i + 1 == searchBeans.size()&&i>1) {
					searchBeans.get(i).getArticleBean()
							.setUp_id(searchBeans.get(i - 1).getArticleBean().getArticle_id());
					searchBeans.get(i).getArticleBean()
							.setUp_name(searchBeans.get(i - 1).getArticleBean().getArticle_name());
				} 
				else {
					searchBeans.get(i).getArticleBean()
							.setNext_id(searchBeans.get(i + 1).getArticleBean().getArticle_id());
					searchBeans.get(i).getArticleBean()
							.setNext_name(searchBeans.get(i + 1).getArticleBean().getArticle_name());
					searchBeans.get(i).getArticleBean()
							.setUp_id(searchBeans.get(i - 1).getArticleBean().getArticle_id());
					searchBeans.get(i).getArticleBean()
							.setUp_name(searchBeans.get(i - 1).getArticleBean().getArticle_name());
				}
				i++;
			} while (i < searchBeans.size()
					&& !(searchBeans.get(i - 1).getArticleBean().getArticle_id().equals(id)));
			if (searchBeans.get(i - 1).getArticleBean().getArticle_id().equals(id)) {
					articleBean = searchBeans.get(i-1).getArticleBean();
				}
			
			model.addAttribute("article", articleBean);
			model.addAttribute("action", "search");
			ServletContext application = req.getServletContext();
			Update_View(application, id);
			return "/WEB-INF/view/AllPublic_article.jsp";
		}
		else {
			return "redirect:"+ "Error.action";
		}
	}
	
	
	@RequestMapping(value="/homepage.action",method = RequestMethod.GET)
	public String homepage(Model model ){
		
		// 点赞量排序前10
		List<ArticleBean> articleBeans = null;
		articleBeans = articleService.getBrief_Article_List_ByLike();
		model.addAttribute("articles_bylike", articleBeans);
		// 下载前10
		articleBeans = articleService.getBrief_Article_list_ByDownload();
		model.addAttribute("articles_byDownload", articleBeans);
		// 浏览量前3
		articleBeans = articleService.getBrief_Article_By_View();
		model.addAttribute("articles_byView", articleBeans);
		// 最新文章前3
		articleBeans = articleService.getBrief_Article_By_Time();
		model.addAttribute("articles_byTime", articleBeans);
		return "/WEB-INF/view/index.jsp";
	}
	
	
	@RequestMapping(value="/editor_page.action",method = RequestMethod.GET)
	public String editor_page(Model model,HttpSession session){
		if (session.getAttribute("username") != null) {
			return "/WEB-INF/view/Editor.jsp";
		} else {
			return "redirect:"+ "login_sign_page.action";
		}
	}
	
	@RequestMapping(value="/login_out.action",method = RequestMethod.GET)
	public String login_out(HttpSession session){
		session.removeAttribute("username");
		session.removeAttribute("headimg");
		return "redirect:"+ "homepage.action";
	}
	
	@RequestMapping(value="/person_info_page.action",method = RequestMethod.GET)
	public String person_info_page(HttpSession session,Model model){
		if (session.getAttribute("username") != null) {
			String uString = (String) session.getAttribute("username");
			User us = userManagerService.getUserBean(uString);
			if (us != null) {
				model.addAttribute("userbean", us);
				return "/WEB-INF/view/person-info.jsp";
			} else {
				return "redirect:"+"person-info.action";
			}
		} else {
			return "redirect:"+"login_sign_page.action";
		}
	}
	
	@RequestMapping(value="/login_sign_page.action",method = RequestMethod.GET)
	public String login_sign_page(Model model,HttpSession session){
		//TODO:初始化验证码取消验证码请
		String creatword = CreatWrod();
		session.setAttribute("login_checkword", creatword);
		model.addAttribute("creatword",creatword);
		return "/WEB-INF/view/login_sign.jsp";
	}
	@RequestMapping(value="/loginContext.action")
	public String loginContext()
	{
		return "redirect:"+"login_sign_page.action";
	}
	
	//TODO:登录
	@RequestMapping(value="/login.action",method = RequestMethod.POST)
	public String login(HttpSession session,@RequestParam(required=false) String checkword,
			@RequestParam(required=false) String username,@RequestParam(required=false) String password,
			Model model){
		List<User> list = new ArrayList<User>(100);
		try {
			if (session.getAttribute("login_checkword") != null) {
				String login_check = session.getAttribute("login_checkword").toString();
				if (checkword.equalsIgnoreCase(login_check)) 
				{
					list = userManagerService.getList(username, password);
					if (list.size() > 0) {
						log.info("登录成功");
						User user = (User) list.get(0);
						session.setAttribute("username", user.getUsername());
						session.setAttribute("headimg", user.getHeadimg());
						session.removeAttribute("login_checkword");
						return "redirect:"+"homepage.action";
					} else {
						log.info("登录失败");
						model.addAttribute("login_flag", "false");
						String creatword = CreatWrod();
						session.setAttribute("login_checkword", creatword);
						model.addAttribute("creatword",creatword);
						return "/WEB-INF/view/login_sign.jsp";
					}
				} else {
					log.info("验证码错");
					model.addAttribute("login_flag", "fail");
					String creatword = CreatWrod();
					session.setAttribute("login_checkword", creatword);
					model.addAttribute("creatword",creatword);
					return "/WEB-INF/view/login_sign.jsp";
				}
			} else {
				log.error("发送验证码为空");
				String creatword = CreatWrod();
				session.setAttribute("login_checkword", creatword);
				model.addAttribute("creatword",creatword);
				return "/WEB-INF/view/login_sign.jsp";
			}
		} catch (Exception e) {
			log.error("登录访问数据库出错" + e.getMessage());
			return "redirect:"+"login_sign_page.action";
		}
	}
	
	//TODO:注册
	@RequestMapping(value="/sign.action",method = RequestMethod.POST)
	public String sign(HttpSession session,@RequestParam(required=false) String username,
			@RequestParam(required=false) String password,@RequestParam(required=false) String checkword,
			Model model){
			User userBean = new User();
			userBean.setUsername(username);
			userBean.setPassword(password);
			String email = session.getAttribute("email")!=null?(String) session.getAttribute("email"):"";
			userBean.setEmail(email);
			if (session.getAttribute("sign_checkword")!=null&&checkword.equalsIgnoreCase(session.getAttribute("sign_checkword").toString())) {
				List<User> userlist = userManagerService.select(username, email);
				if (userlist.size() == 0) {
					int count = userManagerService.inseret(userBean);
					if (count > 0) {
						session.removeAttribute("sign_checkword");
						session.removeAttribute("email");
						return "redirect:"+"login_sign_page.action";
					}
					else {
						log.info("注册出错");
						return "redirect:"+"Error.action";
					}
				} else {
					model.addAttribute("flag", "false");
					String creatword = CreatWrod();
					session.setAttribute("login_checkword", creatword);
					model.addAttribute("creatword",creatword);
					return "/WEB-INF/view/login_sign.jsp";
				}
			} else {
				model.addAttribute("flag", "checkfalse");
				String creatword = CreatWrod();
				session.setAttribute("login_checkword", creatword);
				model.addAttribute("creatword",creatword);
				return "/WEB-INF/view/login_sign.jsp";
			}
	}

	//TODO:发验证码
	@RequestMapping(value="/check.action",method=RequestMethod.POST)
	public void check(HttpSession session,@RequestParam(required=false) String email,HttpServletResponse resp) 
			throws Exception{

		PrintWriter out = resp.getWriter();
		try {
			String creatword = CreatWrod();
			session.setAttribute("sign_checkword", creatword);
			session.setAttribute("email", email);
			SendEmail sendEmail = new SendEmail();
			if (sendEmail.sendEmail("laughing95520@163.com", email, "laughing95520@163.com", "95520wyh",
					creatword)) {
				//TODO:ajax乱码
				String encode = "验证码已发送，请查收";
				//String ou = new String(encode.getBytes("iso-8859-1"),"utf-8");
				resp.setContentType("text/plain;charset=UTF-8");
				out.write(encode);
			}
		} catch (Exception e) {
			out.print("邮箱发送验证码失败");
			log.error("向邮箱发送验证码出错" + e.getMessage());
		} finally {
			out.flush();
			out.close();
		}
	
	}
	
	@RequestMapping(value = "/uploadArticleImg.action", method = RequestMethod.POST)
	public void uploadArticleImg(HttpSession session, HttpServletResponse resp, HttpServletRequest req)
			throws Exception {
		if (session.getAttribute("username") != null) {
			String path = session.getServletContext().getRealPath("/image");
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			String filename = "";
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
			servletFileUpload.setHeaderEncoding("utf-8");
			servletFileUpload.setSizeMax(1024 * 1024 * 2);
			String imgurl = "";
			PrintWriter out = resp.getWriter();
			try {
				List<FileItem> parseRequest = servletFileUpload.parseRequest(req);
				for (FileItem fi : parseRequest) {
					filename = UUID.randomUUID()
							+ fi.getName().substring(fi.getName().lastIndexOf("."), fi.getName().length());
					fi.write(new File(file, filename));
				}
				String uri = req.getRequestURI();
				String requrl = uri.substring(0, uri.lastIndexOf("/"));
				imgurl = requrl + "/image/" + filename;
				resp.setContentType("text/text;charset=utf-8");
				log.info("文章图片上传成功");
			} catch (Exception e) {
				log.error("文章图片上传失败");
			} finally {
				out.print(imgurl); // 返回url地址
				out.flush();
				out.close();
			}
		}
		// TODO:用户登录如何验证？？？
	}
	
	@RequestMapping(value="/publish.action",method = RequestMethod.POST)
	public String publish(HttpSession session,HttpServletRequest req){

		if (session.getAttribute("username") != null) {
			String filePath = session.getServletContext().getRealPath("/WEB-INF/upload");
			File file = new File(filePath);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			try {
				DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
				ServletFileUpload sfu = new ServletFileUpload(diskFileItemFactory);
				sfu.setHeaderEncoding("utf-8");
				List<FileItem> fileItems = sfu.parseRequest(req);
				ArticleBean articleBean = new ArticleBean();
				for (FileItem fileItem : fileItems) {
					if (fileItem.isFormField()) { //判断某项是否是普通的表单类型
						String name = fileItem.getFieldName();
						String value = fileItem.getString("utf-8");
						switch (name) {
						case "article_name": {
							articleBean.setArticle_name(value);
						}
							break;
						case "article_text": {
							articleBean.setArticle_text(value);
						}
							break;
						case "article_from_flag": {
							articleBean.setArticle_from_flag(Integer.valueOf(value));
						}
							break;
						case "article_from": {
							articleBean.setArticle_from(value);
						}
							break;
						case "article_class": {
						}
							break;
						default:
							break;
						}
					} else {
						String filename = fileItem.getName();
						if (filename == null || filename.trim().equals("")) {
							continue;
						}
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
						String flder_name = dateFormat.format(new Date());
						String floder_path = filePath + "\\" + flder_name;
						System.out.println(floder_path);
						File floder = new File(floder_path);
						if (!floder.exists()) {
							floder.mkdirs();
						}
						
						SimpleDateFormat fileDate = new SimpleDateFormat("yyyy-MM-dd");
						String filedate_name = fileDate.format(new Date());
						filename = filedate_name + filename.substring(filename.lastIndexOf("/") + 1);
						InputStream inputStream = fileItem.getInputStream();
						String article_file_path = floder_path + "\\" + filename;
						articleBean.setfile_path(article_file_path);
						long size = fileItem.getSize();
						int article_file_size = (int) (size) / (1024 * 1024) + 1;
						log.info("文件大小" + article_file_size + "M");
						articleBean.setfile_size(article_file_size);
						FileOutputStream outputStream = new FileOutputStream(floder_path + "\\" + filename);
						byte buffer[] = new byte[1024];
						int len = 0;
						while ((len = inputStream.read(buffer)) > 0) {
							outputStream.write(buffer, 0, len);
						}
						inputStream.close();
						outputStream.close();
						fileItem.delete();
						log.info("文件上传成功");
					}
				}
				Date date = new Date();
				SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				java.sql.Timestamp adDate = java.sql.Timestamp.valueOf((sdate).format(date));
				articleBean.setArticle_date(adDate);
				String article_writer = (String) session.getAttribute("username");
				articleBean.setArticle_writer(article_writer);
				String article_id = UUID.randomUUID().toString().replaceAll("-", "");
				articleBean.setArticle_id(article_id);
				articleService.insert(articleBean);
				return "redirect:"+"article_page.action?id=" + articleBean.getArticle_id();
			} catch (Exception e) {
				log.error("发布文章失败" + e.getMessage());
				return "redirect:"+ "Error.action";
			}
		}else {
			log.info("用户未登录");
			return "redirect:"+"login_sign_page.action";
		}
	}
	
	@RequestMapping(value="/article_page.action",method = RequestMethod.GET)
	public String article_page(HttpSession session,@RequestParam(required= false) String id,
			Model model,HttpServletRequest req){
		if (session.getAttribute("username") != null) {
			try {
				String username = (String) session.getAttribute("username");
				ArticleBean articleBean = null;
				String up_id = null;
				String up_name = null;
				String next_id = null;
				String next_name = null;
				List<ArticleBean> articlelist = new ArrayList<ArticleBean>();
				int article_sum = articleService.getSumCount(username).getSum_article_count();
				int index = articleService.getArticle_index(id, username);
				if (index == 0) {
					articlelist = articleService.getArticle(username, index);
					if (articlelist.size() >= 2) {
						articleBean = articlelist.get(0);
						next_id = articlelist.get(1).getArticle_id();
						next_name = articlelist.get(1).getArticle_name();
						articleBean.setNext_id(next_id);
						articleBean.setNext_name(next_name);
					} 
					else if (articlelist.size()==1) {
						articleBean = articlelist.get(0);
					}
					else {
						log.error("查询登录用户的文章出错！");
						return "redirect:"+"Error.action";
					}
				} else if (index < article_sum - 1) {
					articlelist = articleService.getArticle(username, index - 1);
					if (articlelist.size() >= 3) {
						articleBean = articlelist.get(1);
						up_id = articlelist.get(0).getArticle_id();
						up_name = articlelist.get(0).getArticle_name();
						next_id = articlelist.get(2).getArticle_id();
						next_name = articlelist.get(2).getArticle_name();
						articleBean.setUp_id(up_id);
						articleBean.setNext_id(next_id);
						articleBean.setUp_name(up_name);
						articleBean.setNext_name(next_name);
					} else {
						log.error("查询登录用户的文章出错！");
						return "redirect:"+"Error.action";
					}
				} else {
					articlelist = articleService.getArticle(username, index - 1);
					if (articlelist.size() >= 2) {
						articleBean = articlelist.get(1);
						up_id = articlelist.get(0).getArticle_id();
						up_name = articlelist.get(0).getArticle_name();
						articleBean.setUp_id(up_id);
						articleBean.setUp_name(up_name);
					} else {
						log.error("查询登录用户的文章出错！");
						return "redirect:"+"Error.action";
					}
				}
				articlelist = articleService.getArticleList(username);
				model.addAttribute("articleList", articlelist);
				ServletContext application = req.getServletContext();
				Update_View(application, id);
				SimpleDateFormat nd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				java.sql.Timestamp aDate = java.sql.Timestamp.valueOf(nd.format(articleBean.getArticle_date()));
				articleBean.setArticle_date(aDate);
				req.setAttribute("article", articleBean);
				articleBean = articleService.getSumCount(username);
				model.addAttribute("Sum_article_count", articleBean.getSum_article_count());
				model.addAttribute("Sum_view_count", articleBean.getSum_view_count());
				return "/WEB-INF/view/article.jsp";
			} catch (Exception e) {
				log.error(e.getMessage());
				return "redirect:"+"Error.action";
			}
		} else {
			return "redirect:"+"login_sign_page.action";
		}

	}
	
	@RequestMapping(value="article_page_By_download.action",method= RequestMethod.GET)
	public String article_page_By_download(@RequestParam(required=false) String id,Model model,HttpServletRequest req){
		List<ArticleBean> articlelist = new ArrayList<ArticleBean>(15);
		ArticleBean articleBean = new ArticleBean();
		String up_id = null;
		String up_name = null;
		String next_id = null;
		String next_name = null;
		int index = articleService.getArticle_index_By_download(id);
		int article_sum = articleService.Article_Num();
		if (index == 0) {
			articlelist = articleService.getArticleByDownload(index);
			if (articlelist.size() >= 2) {
				articleBean = articlelist.get(0);
				next_id = articlelist.get(1).getArticle_id();
				next_name = articlelist.get(1).getArticle_name();
				articleBean.setNext_id(next_id);
				articleBean.setNext_name(next_name);
			} 
			else if (articlelist.size()==1) {
				articleBean = articlelist.get(0);
			}else {
				log.error("根据Download查询文章出错");
				return "redirect:"+"Error.action";
			}
		} else if (index < article_sum - 1) {
			articlelist = articleService.getArticleByDownload(index - 1);
			if (articlelist.size() >= 3) {
				articleBean = articlelist.get(1);
				up_id = articlelist.get(0).getArticle_id();
				up_name = articlelist.get(0).getArticle_name();
				next_id = articlelist.get(2).getArticle_id();
				next_name = articlelist.get(2).getArticle_name();
				articleBean.setUp_id(up_id);
				articleBean.setNext_id(next_id);
				articleBean.setUp_name(up_name);
				articleBean.setNext_name(next_name);
			} else {
				log.error("根据Download查询文章出错");
				return "redirect:"+"Error.action";
			}
		} else {
			articlelist = articleService.getArticleByDownload(index - 1);
			if (articlelist.size() >= 2) {
				articleBean = articlelist.get(1);
				up_id = articlelist.get(0).getArticle_id();
				up_name = articlelist.get(0).getArticle_name();
				articleBean.setUp_id(up_id);
				articleBean.setUp_name(up_name);
			} else {
				log.error("根据Download查询文章出错");
				return "redirect:"+"Error.action";
			}
		}
		model.addAttribute("article", articleBean);
		model.addAttribute("action", "download");
		ServletContext application = req.getServletContext();
		Update_View(application, id);
		return "/WEB-INF/view/AllPublic_article.jsp";
	}
	
	@RequestMapping(value="/article_page_By_like.action",method = RequestMethod.GET)
	public String article_page_By_like(@RequestParam(required=false) String id,Model model,HttpServletRequest req){
		List<ArticleBean> articlelist = new ArrayList<ArticleBean>(15);
		ArticleBean articleBean = new ArticleBean();
		String up_id = null;
		String up_name = null;
		String next_id = null;
		String next_name = null;
		int index = articleService.getArticle_index_By_like(id);
		int article_sum = articleService.Article_Num();
		if (index == 0) {
			articlelist = articleService.getArticleByLike(index);
			if (articlelist.size() >= 2) {
				articleBean = articlelist.get(0);
				next_id = articlelist.get(1).getArticle_id();
				next_name = articlelist.get(1).getArticle_name();
				articleBean.setNext_id(next_id);
				articleBean.setNext_name(next_name);
			} 
			else if (articlelist.size()==1) {
				articleBean = articlelist.get(0);
			}else {
				log.error("根据Like查询文章出错");
				return "redirect:"+"Error.action";
			}

		} else if (index < article_sum - 1) {
			articlelist = articleService.getArticleByLike(index - 1);
			if (articlelist.size() >= 3) {
				articleBean = articlelist.get(1);
				up_id = articlelist.get(0).getArticle_id();
				up_name = articlelist.get(0).getArticle_name();
				next_id = articlelist.get(2).getArticle_id();
				next_name = articlelist.get(2).getArticle_name();
				articleBean.setUp_id(up_id);
				articleBean.setNext_id(next_id);
				articleBean.setUp_name(up_name);
				articleBean.setNext_name(next_name);
			} else {
				log.error("根据Like查询文章出错");
				return "redirect:"+"Error.action";
			}
		} else {
			articlelist = articleService.getArticleByLike(index - 1);
			if (articlelist.size() >= 2) {
				articleBean = articlelist.get(1);
				up_id = articlelist.get(0).getArticle_id();
				up_name = articlelist.get(0).getArticle_name();
				articleBean.setUp_id(up_id);
				articleBean.setUp_name(up_name);
			} else {
				log.error("根据Like查询文章出错");
				return "redirect:"+"Error.action";
			}
		}
		model.addAttribute("article", articleBean);
		model.addAttribute("action", "like");
		ServletContext application = req.getServletContext();
		Update_View(application, id);
		return "/WEB-INF/view/AllPublic_article.jsp";
	}
	
	@RequestMapping(value="/article_page_By_time.action",method=RequestMethod.GET)
	public String article_page_By_time(@RequestParam(required=false) String id,Model model,HttpServletRequest req){
		List<ArticleBean> articlelist = new ArrayList<ArticleBean>();
		ArticleBean articleBean = new ArticleBean();
		String up_id = null;
		String up_name = null;
		String next_id = null;
		String next_name = null;
		int index = articleService.getArticle_index_By_time(id);
		int article_sum = articleService.Article_Num();
		if (index == 0) {
			articlelist = articleService.getArticleByTime(index);
			if (articlelist.size() >= 2) {
				articleBean = articlelist.get(0);
				next_id = articlelist.get(1).getArticle_id();
				next_name = articlelist.get(1).getArticle_name();
				articleBean.setNext_id(next_id);
				articleBean.setNext_name(next_name);
			} 
			else if (articlelist.size()==1) {
				articleBean = articlelist.get(0);
			}else {
				log.error("根据Time查询文章出错");
				return "redirect:"+"Error.action";
			}
		} else if (index < article_sum - 1) {
			articlelist = articleService.getArticleByTime(index - 1);
			if (articlelist.size() >= 3) {
				articleBean = articlelist.get(1);
				up_id = articlelist.get(0).getArticle_id();
				up_name = articlelist.get(0).getArticle_name();
				next_id = articlelist.get(2).getArticle_id();
				next_name = articlelist.get(2).getArticle_name();
				articleBean.setUp_id(up_id);
				articleBean.setNext_id(next_id);
				articleBean.setUp_name(up_name);
				articleBean.setNext_name(next_name);
			} else {
				log.error("根据Time查询文章出错");
				return "redirect:"+"Error.action";
			}
		} else {
			articlelist = articleService.getArticleByTime(index - 1);
			if (articlelist.size() >= 2) {
				articleBean = articlelist.get(1);
				up_id = articlelist.get(0).getArticle_id();
				up_name = articlelist.get(0).getArticle_name();
				articleBean.setUp_id(up_id);
				articleBean.setUp_name(up_name);
			} else {
				log.error("根据Time查询文章出错");
				return "redirect:"+"Error.action";
			}
		}
		model.addAttribute("article", articleBean);
		model.addAttribute("action", "time");
		ServletContext application = req.getServletContext();
		Update_View(application, id);
		return "/WEB-INF/view/AllPublic_article.jsp";
	}
	@RequestMapping(value="/article_page_By_view.action",method=RequestMethod.GET)
	public String article_page_By_view(HttpServletRequest req,Model model,@RequestParam(required=false) String id){
		List<ArticleBean> articlelist = new ArrayList<ArticleBean>();
		ArticleBean articleBean = new ArticleBean();
		String up_id = null;
		String up_name = null;
		String next_id = null;
		String next_name = null;
		int index = articleService.getArticle_index_By_view(id);
		int article_sum = articleService.Article_Num();
		if (index == 0) {
			articlelist = articleService.getArticleByView(index);
			if (articlelist.size() >= 2) {
				articleBean = articlelist.get(0);
				next_id = articlelist.get(1).getArticle_id();
				next_name = articlelist.get(1).getArticle_name();
				articleBean.setNext_id(next_id);
				articleBean.setNext_name(next_name);
			} 
			else if (articlelist.size()==1) {
				articleBean = articlelist.get(0);
			}else {
				log.error("根据View查询文章出错");
				return "redirect:"+"Error.action";
			}
		} else if (index < article_sum - 1) {
			articlelist = articleService.getArticleByView(index - 1);
			if (articlelist.size() >= 3) {
				articleBean = articlelist.get(1);
				up_id = articlelist.get(0).getArticle_id();
				up_name = articlelist.get(0).getArticle_name();
				next_id = articlelist.get(2).getArticle_id();
				next_name = articlelist.get(2).getArticle_name();
				articleBean.setUp_id(up_id);
				articleBean.setNext_id(next_id);
				articleBean.setUp_name(up_name);
				articleBean.setNext_name(next_name);
			} else {
				log.error("根据View查询文章出错");
				return "redirect:"+"Error.action";
			}
		} else {
			articlelist = articleService.getArticleByView(index - 1);
			if (articlelist.size() >= 2) {
				articleBean = articlelist.get(1);
				up_id = articlelist.get(0).getArticle_id();
				up_name = articlelist.get(0).getArticle_name();
				articleBean.setUp_id(up_id);
				articleBean.setUp_name(up_name);
			} else {
				log.error("根据View查询文章出错");
				return "redirect:"+"Error.action";
			}
		}
		model.addAttribute("article", articleBean);
		model.addAttribute("action", "view");
		ServletContext application = req.getServletContext();
		Update_View(application, id);
		return "/WEB-INF/view/AllPublic_article.jsp";
	}
	
	@RequestMapping(value="/download.action",method=RequestMethod.GET)
	public String download(HttpSession session,@RequestParam(required=false) String id,HttpServletResponse resp){
		if (session.getAttribute("username") != null) {
			try {
				String file_path = articleService.getfile_path(id);
				File file = new File(file_path);
				if (!file.exists()) {
					log.info("文件不存在！");
					return "redirect:"+"Error.action";
				}
				String realname = file_path.substring(file_path.lastIndexOf("\\") + 1);
				resp.setHeader("content-disposition",
						"attachment;filename=" + URLEncoder.encode(realname, "utf-8"));
				FileInputStream inputStream = new FileInputStream(file_path);
				OutputStream outputStream = resp.getOutputStream();
				byte buffer[] = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, len);
				}
				inputStream.close();
				outputStream.close();
				int i = articleService.update_file_count(id);
				if (i < 0) {
					log.info("更新文章下载次数成功");
				} else {
					log.info("更新文章下载次数失败");
				}
			} catch (Exception e) {
				log.error("文件下载出错");
				return "redirect:"+"Error.action";
			}
		}
		return "redirect:"+"login_sign_page.action";
	}
	
	@RequestMapping(value="/like.action",method=RequestMethod.POST)
	public String like(HttpSession session,@RequestParam(required=false) String id,HttpServletResponse resp){
		if (session.getAttribute("username") != null) {
			try {
				String username1 = (String) session.getAttribute("username");
				username1 = "laughing";
				int count = articleService.insert_like_count(id, username1);
				if (count > 0) {
					count = articleService.artile_like_count(id);
					PrintWriter out = resp.getWriter();
					out.print(count);
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				log.error("点赞异常" + e.getMessage());
				return "redirect:"+"Error.action";
			}
		}
		return "redirect:"+"login_sign_page.action";
	}
	
	@RequestMapping(value="/Time_list_page.action",method=RequestMethod.GET)
	public String Time_list_page(@RequestParam(required=false) String page,Model model){
		List<ArticleBean> articleList = new ArrayList<>(15);
		int article_num = articleService.Article_Num();
		int sumPage = ((article_num % 10) > 0) ? ((article_num / 10) + 1) : (article_num / 10);
		if (page != null) {
			int pageInt = Integer.valueOf(page);
			articleList = articleService.getArticle_time_list((pageInt - 1) * 10);
			model.addAttribute("currentPage", pageInt);
			if (pageInt + 10 <= sumPage) {
				model.addAttribute("end", pageInt + 10);
			} else {
				model.addAttribute("end", sumPage);
			}
			model.addAttribute("articleList", articleList);
		} else {
			articleList = articleService.getArticle_time_list(0);
			model.addAttribute("currentPage", 1);
			if (sumPage <= 10) {
				model.addAttribute("end", sumPage);
			} else {
				model.addAttribute("end", 10);
			}
			model.addAttribute("articleList", articleList);
		}
		model.addAttribute("sumPage", sumPage);
		model.addAttribute("action", "time");
		return "/WEB-INF/view/All_article_list.jsp";
	}
	
	@RequestMapping(value="/View_list_page.action",method=RequestMethod.GET)
	public String View_list_page(@RequestParam(required=false) String page,Model model){
		List<ArticleBean> articleList = new ArrayList<>(15);
		int article_num = articleService.Article_Num();
		int sumPage = ((article_num % 10) > 0) ? ((article_num / 10) + 1) : (article_num / 10);
		if (page!= null) {
			int pageInt = Integer.valueOf(page);
			articleList = articleService.getArticle_view_list((pageInt - 1) * 10);
			model.addAttribute("currentPage", pageInt);
			if (pageInt + 10 <= sumPage) {
				model.addAttribute("end", pageInt + 10);
			} else {
				model.addAttribute("end", sumPage);
			}
			model.addAttribute("articleList", articleList);
		} else {
			articleList = articleService.getArticle_view_list(0);
			model.addAttribute("currentPage", 1);
			if (sumPage <= 10) {
				model.addAttribute("end", sumPage);
			} else {
				model.addAttribute("end", 10);
			}
			model.addAttribute("articleList", articleList);
		}
		model.addAttribute("sumPage", sumPage);
		model.addAttribute("action", "view");
		return "/WEB-INF/view/All_article_list.jsp";
	}
	
	@RequestMapping(value="/article_list_page.action",method=RequestMethod.GET)
	public String article_list_page(HttpSession session,Model model,@RequestParam(required=false) String page){
		if (session.getAttribute("username") != null) {
			String username = (String) session.getAttribute("username");
			List<ArticleBean> articleList = new ArrayList<>(15);
			ArticleBean articleBean = articleService.getSumCount(username);
			int article_count = articleBean.getSum_article_count();
			model.addAttribute("Sum_article_count", article_count);
			model.addAttribute("Sum_view_count", articleBean.getSum_view_count());
			articleList = articleService.getArticleList(username);
			model.addAttribute("article_brief_List", articleList);
			int sumPage = ((article_count % 10) > 0) ? ((article_count / 10) + 1) : (article_count / 10);
			if (page != null) {
				int pageInt = Integer.valueOf(page);
				articleList = articleService.getPageList(username, ((pageInt - 1) * 10));
				model.addAttribute("currentPage", pageInt);
				if (pageInt + 10 <= sumPage) {
					model.addAttribute("end", pageInt + 10);
				} else {
					model.addAttribute("end", sumPage);
				}
				model.addAttribute("articleList", articleList);
			} else {
				articleList = articleService.getPageList(username, 0);
				model.addAttribute("currentPage", 1);
				if (sumPage <= 10) {
					model.addAttribute("end", sumPage);
				} else {
					model.addAttribute("end", 10);
				}
				model.addAttribute("articleList", articleList);
			}
			model.addAttribute("sumPage", sumPage);
			return "/WEB-INF/view/article_list.jsp";
		} 
		return "redirect:"+"login_sign_page.action";
		
	}
	
	@RequestMapping(value="/Like_list_page.action",method=RequestMethod.GET)
	public String Like_list_page(@RequestParam(required=false) String page,Model model){
		List<ArticleBean> articleList = new ArrayList<>(15);
		int article_num = articleService.Article_Num();
		int sumPage = ((article_num % 10) > 0) ? ((article_num / 10) + 1) : (article_num / 10);
		if (page != null) {
			int pageInt = Integer.valueOf(page);
			articleList = articleService.getArticle_like_list((pageInt - 1) * 10);
			model.addAttribute("currentPage", pageInt);
			if (pageInt + 10 <= sumPage) {
				model.addAttribute("end", pageInt + 10);
			} else {
				model.addAttribute("end", sumPage);
			}
			model.addAttribute("articleList", articleList);
		} else {
			articleList = articleService.getArticle_like_list(0);
			model.addAttribute("currentPage", 1);
			if (sumPage <= 10) {
				model.addAttribute("end", sumPage);
			} else {
				model.addAttribute("end", 10);
			}
			model.addAttribute("articleList", articleList);
		}
		model.addAttribute("sumPage", sumPage);
		model.addAttribute("action", "like");
		return "/WEB-INF/view/All_article_list.jsp";
	}
	
	@RequestMapping(value="/Download_list_page.action",method=RequestMethod.GET)
	public String Download_list_page(@RequestParam(required=false) String page,Model model){
		List<ArticleBean> articleList = new ArrayList<>();
		int article_num = articleService.Article_Num();
		int sumPage = ((article_num % 10) > 0) ? ((article_num / 10) + 1) : (article_num / 10);
		if (page != null) {
			int pageInt = Integer.valueOf(page);
			articleList = articleService.getArticle_download_list((pageInt - 1) * 10);
			model.addAttribute("currentPage", pageInt);
			if (pageInt + 10 <= sumPage) {
				model.addAttribute("end", pageInt + 10);
			} else {
				model.addAttribute("end", sumPage);
			}
			model.addAttribute("articleList", articleList);
		} else {
			articleList = articleService.getArticle_download_list(0);
			model.addAttribute("currentPage", 1);
			if (sumPage <= 10) {
				model.addAttribute("end", sumPage);
			} else {
				model.addAttribute("end", 10);
			}
			model.addAttribute("articleList", articleList);
		}
		model.addAttribute("sumPage", sumPage);
		model.addAttribute("action", "download");
		return "/WEB-INF/view/All_article_list.jsp";
	}
	
	@RequestMapping(value="/Error.action",method=RequestMethod.GET)
	public String Error(){
		return "/WEB-INF/view/error.html";
	}

	//TODO:from UserInfoServlet
	@RequestMapping(value="/save_info.action",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	public String save_info(HttpSession session,@RequestParam(required=false) String email,
			@RequestParam(required=false) String username,@RequestParam(required=false) String place,
			@RequestParam(required=false) String sex,@RequestParam(required=false) String date,
			@RequestParam(required=false) String id){
		if (session.getAttribute("username") != null) {
			try {
				User user = new User();
				int sexInt = Integer.valueOf(sex);
				int idInt = Integer.valueOf(id).intValue();
				user.setUsername(username);
				user.setEmail(email);
				user.setPlace(place);
				user.setSex(sexInt);
				user.setBirthday(date);
				place = new String(place.getBytes("iso-8859-1"),"utf-8");
				user.setId(idInt);
				boolean isUpdate = userManagerService.saveUser(user);
				log.info("用户信息存储"+isUpdate);
				return "redirect:"+"person_info_page.action";
			} catch (Exception e) {
				log.error("用户信息更新失败"+e.getMessage());
				return "redirect:"+"Error.action";
			}
		} else {
			return "redirect:"+"login_sign_page.action";
		}
	}
	
	@RequestMapping(value="/uploadImg.action",method=RequestMethod.POST)
	public String uploadImg(HttpSession session,HttpServletRequest req,HttpServletResponse resp){
		if (session.getAttribute("username") != null) {
			String path = session.getServletContext().getRealPath("/image");
			File file = new File(path);
			System.out.println(file.exists());
			if (!file.exists()) {
				file.mkdirs();
			}
			String fileName = "";
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
			servletFileUpload.setHeaderEncoding("utf-8");
			servletFileUpload.setSizeMax(1024 * 1024);
			try {
				List<FileItem> paresRequest = servletFileUpload.parseRequest(req);
				for (FileItem fileItem : paresRequest) {
					fileName = UUID.randomUUID() + fileItem.getName().substring(fileItem.getName().lastIndexOf("."),
							fileItem.getName().length());
					fileItem.write(new File(path, fileName));
				}
				String url = req.getRequestURI();
				String requrl = url .substring(0, url.lastIndexOf("/"));
				String imgurl = requrl + "/image/" + fileName;
				resp.setContentType("text/text;charset=utf-8");
				String username = (String)session.getAttribute("username");
				session.setAttribute("headimg", imgurl);
				boolean isupload = userManagerService.uploadPhoto(imgurl, username);
				log.info("头像更新isupload:" + isupload);
				return "redirect:"+ "person_info_page.action";
			} catch (Exception e) {
				log.error("用户头像上传失败");
				return "redirect:"+ "Error.action";
			}
		} else {
			return "redirect:"+"login_sign_page.action";
		}
	}
	public String CreatWrod() {
		String str = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789";
		String result = "";
		for (int i = 0; i < 4; i++) {
			Random random = new Random();
			int index = random.nextInt(str.length());
			result = result + str.charAt(index);
		}
		return result;
	}
	public void Update_View(ServletContext application, String id) {

		Object attribute = application.getAttribute("view_count");
		if ((attribute instanceof Map) && id != "") {
			@SuppressWarnings("unchecked")
			HashMap<String, Integer> map = (HashMap<String, Integer>) attribute;
			if (map.containsKey(id)) {
				int count = map.get(id);
				map.put(id, count + 1);
			} else {
				map.put(id, 1);
			}
			application.setAttribute("view_count", map);
		}
	}
}