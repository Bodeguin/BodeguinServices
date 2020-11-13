using AutoMapper;
using Bodeguin.Application.Communication.Response;
using Bodeguin.Application.Service;
using Bodeguin.Domain.Entity;
using Bodeguin.Infraestructure.Context;
using Bodeguin.Infraestructure.Repository;
using Microsoft.EntityFrameworkCore;
using System;
using Xunit;

namespace XUnitTest
{
    public class CategoryUnitTest
    {
        [Fact]
        public async void TestListCategories()
        {
            var options = new DbContextOptionsBuilder<AppDbContext>()
                .UseInMemoryDatabase(databaseName: "Test").Options;

            var config = new MapperConfiguration(cfg => cfg.
                CreateMap<Category, CategoryResponse>()
                    .ForMember(cr => cr.Id, cr => cr.MapFrom(c => c.Id))
                    .ForMember(cr => cr.Name, cr => cr.MapFrom(c => c.Name))
                    .ForMember(cr => cr.UrlImage, cr => cr.MapFrom(c => c.UrlImage))
                    .ForMember(cr => cr.NumProducts, cr => cr.MapFrom(c => c.Products.Count))
            );
            IMapper mapper = new Mapper(config);
            var context = new AppDbContext(options);
            var unitOfWork = new UnitOfWork(context);
            var fakeCategory = new Category()
            {
                Id = 1,
                Name = "Fideos",
                CreateAt = DateTime.Now,
                Description = "Description",
                IsActive = true,
                ModifiedAt = DateTime.Now,
                UrlImage = "https://www.google.com"
            };

            await unitOfWork.CategoryRepository.AddAsync(fakeCategory, null);
            await unitOfWork.SaveChangesAsync();

            var categoryService = new CategoryService(unitOfWork, mapper);
            var result = await categoryService.GetCategories();
            Assert.True(result.Valid);
            Assert.NotNull(result.Data);
        }
    }
}
