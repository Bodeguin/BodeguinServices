using AutoMapper;
using Bodeguin.Application.Communication.Response;
using Bodeguin.Application.Service;
using Bodeguin.Domain.Entity;
using Bodeguin.Domain.Enums;
using Bodeguin.Infraestructure.Context;
using Bodeguin.Infraestructure.Repository;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;
using Xunit;

namespace XUnitTest
{
    public class ProductUnitTest
    {
        [Fact]
        public async void TestSearchProduct()
        {
            var options = new DbContextOptionsBuilder<AppDbContext>()
                .UseInMemoryDatabase(Guid.NewGuid().ToString()).Options;

            var config = new MapperConfiguration(cfg => cfg.
                CreateMap<Product, ProductResponse>()
                    .ForMember(pr => pr.Id, pr => pr.MapFrom(p => p.Id))
                    .ForMember(pr => pr.Name, pr => pr.MapFrom(p => p.Name))
                    .ForMember(pr => pr.Description, pr => pr.MapFrom(p => p.Description))
                    .ForMember(pr => pr.UrlImage, pr => pr.MapFrom(p => p.UrlImage))
                    .ForMember(pr => pr.NumStore, pr => pr.MapFrom(p => p.Inventories.Count))
            );

            IMapper mapper = new Mapper(config);
            var context = new AppDbContext(options);
            var unitOfWork = new UnitOfWork(context);
            var fakeProduct = new Product
            {
                Id = 1,
                Name = "leche evaporada",
                Description = "Descripcion",
                CategoryId = 2,
                CreateAt = DateTime.Now,
                ModifiedAt = DateTime.Now,
                IsActive = true,
                UrlImage = "https://wwww.google.com"
            };

            await unitOfWork.ProductRepository.AddAsync(fakeProduct, null);
            await unitOfWork.SaveChangesAsync();

            var productService = new ProductService(unitOfWork, mapper);
            var result = await productService.GetSearchProduct("eva");

            Assert.True(result.Valid);
            Assert.NotNull(result.Data);
            Assert.Single(result.Data);
        }

        [Fact]
        public async void TestStoresByProduct()
        {
            var options = new DbContextOptionsBuilder<AppDbContext>()
                .UseInMemoryDatabase(Guid.NewGuid().ToString()).Options;

            var config = new MapperConfiguration(cfg => cfg.
                CreateMap<Inventory, ProductStoreResponse>()
                    .ForMember(pr => pr.Id, pr => pr.MapFrom(i => i.Id))
                    .ForMember(pr => pr.Quantity, pr => pr.MapFrom(i => i.Quantity))
                    .ForMember(pr => pr.Price, pr => pr.MapFrom(i => i.Price.ToString("0.00")))
                    .ForMember(pr => pr.MeasureUnit, pr => pr.MapFrom(i => ((MeasureUnit)i.MeasureUnit).ToString()))
                    .ForMember(pr => pr.Store, pr => pr.MapFrom(i => i.Store.Name))
                    .ForMember(pr => pr.UrlImageProduct, pr => pr.MapFrom(i => i.Product.UrlImage))
                    .ForMember(pr => pr.Product, pr => pr.MapFrom(i => i.Product.Name))
                    .ForMember(pr => pr.Latitude, pr => pr.MapFrom(i => i.Store.Latitude))
                    .ForMember(pr => pr.Longitude, pr => pr.MapFrom(i => i.Store.Longitude))
            );

            IMapper mapper = new Mapper(config);
            var context = new AppDbContext(options);
            var unitOfWork = new UnitOfWork(context);
            var fakeStore = new Store
            {
                Id = 1,
                Description = "Decription",
                CreateAt = DateTime.Now,
                ModifiedAt = DateTime.Now,
                Direction = "Direction",
                IsActive = true,
                Latitude = 12.459874,
                Longitude = -76.514897,
                Name = "Tiendita",
                Ruc = "10541236549"
            };
            var fakeProduct = new Product
            {
                Id = 1,
                Name = "Mariscos",
                Description = "Descripcion",
                CategoryId = 2,
                CreateAt = DateTime.Now,
                ModifiedAt = DateTime.Now,
                IsActive = true,
                UrlImage = "https://wwww.google.com"
            };
            var fakeInventory = new Inventory
            {
                Id = 1,
                CreateAt = DateTime.Now,
                ModifiedAt = DateTime.Now,
                IsActive = true,
                MeasureUnit = 1,
                Price = 12.5f,
                ProductId = 1,
                Quantity = 10,
                StoreId = 1,
                Product = fakeProduct,
                Store = fakeStore
            };

            await unitOfWork.StoreRepository.AddAsync(fakeStore, null);
            await unitOfWork.ProductRepository.AddAsync(fakeProduct, null);
            await unitOfWork.InventoryRepository.AddAsync(fakeInventory, null);
            await unitOfWork.SaveChangesAsync();

            var procductService = new ProductService(unitOfWork, mapper);
            var result = await procductService.GetStoreByProduct(1);
            Assert.True(result.Valid);
            Assert.NotNull(result.Data);
            Assert.Single(result.Data);
        }
    }
}
