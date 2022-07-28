package com.techtalentsouth.TechTalentBlog.BlogPost;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	// TODO: Read a single blog post at a time.

	@GetMapping(value = "/blogposts/{id}")
	public String viewPostById(@PathVariable Long id, BlogPost blogpost, Model model) {
		// 1. I need the blogpost data for that id.
		Optional<BlogPost> optionalBlogPost = blogPostRepository.findById(id);
		// FIXME: What if there is no blogpost found with that id?
		BlogPost blogPost = optionalBlogPost.get();
		// 2. I need to display the result page.
			model.addAttribute("title", blogPost.getTitle());
			model.addAttribute("author", blogPost.getAuthor());
			model.addAttribute("blogEntry", blogPost.getBlogEntry());

		return "blogpost/result";
	}

	@GetMapping(value = "/blogposts/new")
	public String newBlog(BlogPost blogPost) {
		return "blogpost/new";
	}

	@PostMapping(value = "/blogposts")
	public String addNewBlogPost(BlogPost blogPost, Model model) {
		BlogPost savedBlogPost = blogPostRepository
				.save(new BlogPost(blogPost.getTitle(), blogPost.getAuthor(), blogPost.getBlogEntry()));
		model.addAttribute("title", savedBlogPost.getTitle());
		model.addAttribute("author", savedBlogPost.getAuthor());
		model.addAttribute("blogEntry", savedBlogPost.getBlogEntry());
		// TODO: Build the single blogpost page, and redirect there instead.
		return "blogpost/result";
	}

	// TODO: GET Route to display the Edit page

	// TODO: PUT Route to actually edit the page from the Edit page form submission

	@RequestMapping(value = "/blogposts/{id}", method = RequestMethod.DELETE)
	public String deletePostWithId(@PathVariable Long id, BlogPost blogPost) {
		blogPostRepository.deleteById(id);
		return "redirect:/";
	}
}
