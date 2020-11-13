using System;
using AutoMapper;
using Bodeguin.Application.Communication.Response;
using Bodeguin.Application.Service;
using Bodeguin.Domain.Entity;
using Bodeguin.Infraestructure.Context;
using Bodeguin.Infraestructure.Repository;
using Microsoft.EntityFrameworkCore;
using Xunit;

namespace XUnitTest
{
    public class StoreUnitTest
    {
        [Fact]
        public async void TestListStores()
        {
            var options = new DbContextOptionsBuilder<PostgreSqlContext>()
                .UseInMemoryDatabase(databaseName: "Test").Options;

            var config = new MapperConfiguration(cfg => cfg.
                CreateMap<Store, StoreResponse>()
                    .ForMember(sr => sr.Id, sr => sr.MapFrom(s => s.Id))
                    .ForMember(sr => sr.Name, sr => sr.MapFrom(s => s.Name))
                    .ForMember(sr => sr.Direction, sr => sr.MapFrom(s => s.Direction))
                    .ForMember(sr => sr.Latitude, sr => sr.MapFrom(s => s.Latitude))
                    .ForMember(sr => sr.Longitude, sr => sr.MapFrom(s => s.Longitude))
            );

            IMapper mapper = new Mapper(config);

            var context = new PostgreSqlContext(options);

            var unitOfWork = new UnitOfWork(context);
            var fakeStore = new Store()
            {
                Id = 1,
                Name = "Tienda_01",
                CreateAt = DateTime.Now,
                Description = "Description",
                Direction = "direction",
                IsActive = true,
                Latitude = 12.541246,
                Longitude = -76.154879,
                ModifiedAt = DateTime.Now,
                Ruc = "20548716549"
            };

            await unitOfWork.StoreRepository.AddAsync(fakeStore, null);
            await unitOfWork.SaveChangesAsync();

            var storeService = new StoreService(unitOfWork, mapper);
            var result = await storeService.GetStores();
            Assert.True(result.Valid);
        }
    }
}
