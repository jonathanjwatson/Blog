package com.techtalentsouth.TechTalentBlog.BlogPost;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BlogPostController {

	@Autowired
	private BlogPostRepository blogPostRepository;
//	private static List<BlogPost> posts = new ArrayList<>();

	@GetMapping(value = "/")
	public String index(BlogPost blogPost, Model model) {
		model.addAttribute("posts", blogPostRepository.findAll());
		return "blogpost/index";
	}

	@GetMapping(value = "/blogposts/new")
	public String newBlog(BlogPost blogPost) {
		return "blogpost/new";
	}

	@PostMapping(value = "/blogposts")
	public String addNewBlogPost(BlogPost blogPost, Model model) {
		BlogPost savedBlogPost = blogPostRepository.save(new BlogPost(blogPost.getTitle(), blogPost.getAuthor(), blogPost.getBlogEntry()));
		model.addAttribute("title", savedBlogPost.getTitle());
		model.addAttribute("author", savedBlogPost.getAuthor());
		model.addAttribute("blogEntry", savedBlogPost.getBlogEntry());
		// TODO: Build the single blogpost page, and redirect there instead.
		return "blogpost/result";
	}

	@RequestMapping(value = "/blogposts/{id}", method = RequestMethod.DELETE)
	public String deletePostWithId(@PathVariable Long id, BlogPost blogPost) {
		blogPostRepository.deleteById(id);
		return "redirect:/";
	}
}
