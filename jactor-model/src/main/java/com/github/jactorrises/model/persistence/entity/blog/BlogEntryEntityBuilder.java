package com.github.jactorrises.model.persistence.entity.blog;

import com.github.jactorrises.model.Builder;

public class BlogEntryEntityBuilder extends Builder<BlogEntryEntity> {
    private BlogEntity blogEntity;
    private String entry;
    private String name;

    BlogEntryEntityBuilder() {
    }

    public BlogEntryEntityBuilder with(BlogEntity blogEntity) {
        this.blogEntity = blogEntity;
        return this;
    }

    public BlogEntryEntityBuilder withEntry(String entry) {
        this.entry = entry;
        return this;
    }

    public BlogEntryEntityBuilder withCreatorName(String name) {
        this.name = name;
        return this;
    }

    @Override protected BlogEntryEntity buildBean() {
        BlogEntryEntity blogEntryEntity = new BlogEntryEntity();
        blogEntryEntity.setBlog(blogEntity);
        blogEntryEntity.setCreatorName(name);
        blogEntryEntity.setEntry(entry);

        return blogEntryEntity;
    }
}