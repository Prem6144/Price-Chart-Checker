require 'rubygems'
require 'sitemap_generator'

SitemapGenerator::Sitemap.default_host = 'abc.abc.com'
routes = `rake routes`
puts routes.first
# routes.each do |r|
# puts r.keys
# end

SitemapGenerator::Sitemap.create do

  add '/website/home/', :changefreq => 'daily', :priority => 1

end
SitemapGenerator::Sitemap.ping_search_engines # Not needed if you use the rake tasks
