USE [master]
GO
/****** Object:  Database [libreria]    Script Date: 02/02/2020 16:03:04 ******/
CREATE DATABASE [libreria]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'libreria', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\libreria.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'libreria_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\libreria_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [libreria] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [libreria].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [libreria] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [libreria] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [libreria] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [libreria] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [libreria] SET ARITHABORT OFF 
GO
ALTER DATABASE [libreria] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [libreria] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [libreria] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [libreria] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [libreria] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [libreria] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [libreria] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [libreria] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [libreria] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [libreria] SET  ENABLE_BROKER 
GO
ALTER DATABASE [libreria] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [libreria] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [libreria] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [libreria] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [libreria] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [libreria] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [libreria] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [libreria] SET RECOVERY FULL 
GO
ALTER DATABASE [libreria] SET  MULTI_USER 
GO
ALTER DATABASE [libreria] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [libreria] SET DB_CHAINING OFF 
GO
ALTER DATABASE [libreria] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [libreria] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [libreria] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'libreria', N'ON'
GO
ALTER DATABASE [libreria] SET QUERY_STORE = OFF
GO
USE [libreria]
GO
/****** Object:  Schema [libreria]    Script Date: 02/02/2020 16:03:04 ******/
CREATE SCHEMA [libreria]
GO
/****** Object:  Schema [m2ss]    Script Date: 02/02/2020 16:03:04 ******/
CREATE SCHEMA [m2ss]
GO
/****** Object:  Table [libreria].[autor]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[autor](
	[IdAutor] [int] IDENTITY(10,1) NOT NULL,
	[NombreAutor] [nvarchar](45) NOT NULL,
	[EstadoAutor] [smallint] NOT NULL,
 CONSTRAINT [PK_autor_IdAutor] PRIMARY KEY CLUSTERED 
(
	[IdAutor] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [autor$NombreAutor_UNIQUE] UNIQUE NONCLUSTERED 
(
	[NombreAutor] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[categoriaempleado]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[categoriaempleado](
	[IdCategoriaEmpleado] [int] IDENTITY(7,1) NOT NULL,
	[NombreCategoriaEmpleado] [nvarchar](45) NOT NULL,
	[SueldoBasicoCategoria] [real] NULL,
 CONSTRAINT [PK_categoriaempleado_IdCategoriaEmpleado] PRIMARY KEY CLUSTERED 
(
	[IdCategoriaEmpleado] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [categoriaempleado$NombreCategoriaEmpleado_UNIQUE] UNIQUE NONCLUSTERED 
(
	[NombreCategoriaEmpleado] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[cliente]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[cliente](
	[IdCliente] [int] IDENTITY(4,1) NOT NULL,
	[DniCliente] [int] NULL,
	[CuitCliente] [nvarchar](15) NULL,
	[NombreCliente] [nvarchar](45) NOT NULL,
	[DireccionCliente] [nvarchar](45) NOT NULL,
	[TelefonoCliente] [nvarchar](45) NULL,
	[EmailCliente] [nvarchar](45) NULL,
	[IdLocalidadCliente] [int] NOT NULL,
	[IdTipoCliente] [int] NOT NULL,
	[EstadoCliente] [smallint] NOT NULL,
 CONSTRAINT [PK_cliente_IdCliente] PRIMARY KEY CLUSTERED 
(
	[IdCliente] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[editorial]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[editorial](
	[IdEditorial] [int] IDENTITY(9,1) NOT NULL,
	[NombreEditorial] [nvarchar](45) NOT NULL,
	[DireccionEditorial] [nvarchar](45) NULL,
	[TelefonoEditorial] [nvarchar](45) NULL,
	[EstadoEditorial] [smallint] NOT NULL,
 CONSTRAINT [PK_editorial_IdEditorial] PRIMARY KEY CLUSTERED 
(
	[IdEditorial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[empleado]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[empleado](
	[IdEmpleado] [int] IDENTITY(3,1) NOT NULL,
	[DniEmpleado] [nvarchar](45) NOT NULL,
	[NombreEmpleado] [nvarchar](45) NOT NULL,
	[DomicilioEmpleado] [nvarchar](45) NULL,
	[TelefonoEmpleado] [nvarchar](45) NULL,
	[IdLocalidad] [int] NOT NULL,
	[FechaIngreso] [date] NULL,
	[IdCategoriaEmpleado] [int] NOT NULL,
	[IdEstadoCivilEmpleado] [int] NOT NULL,
	[IdUsuario] [int] NULL,
	[EstadoEmpleado] [smallint] NOT NULL,
 CONSTRAINT [PK_empleado_IdEmpleado] PRIMARY KEY CLUSTERED 
(
	[IdEmpleado] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [empleado$DniEmpleado_UNIQUE] UNIQUE NONCLUSTERED 
(
	[DniEmpleado] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[estadocivilempleado]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[estadocivilempleado](
	[IdEstadoCivilEmpleado] [int] IDENTITY(5,1) NOT NULL,
	[NombreEstadoCivilEmpleado] [nvarchar](45) NOT NULL,
 CONSTRAINT [PK_estadocivilempleado_IdEstadoCivilEmpleado] PRIMARY KEY CLUSTERED 
(
	[IdEstadoCivilEmpleado] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [estadocivilempleado$NombreEstadoCivilEmpleado_UNIQUE] UNIQUE NONCLUSTERED 
(
	[NombreEstadoCivilEmpleado] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[facturaventa]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[facturaventa](
	[IdFacturaVenta] [int] IDENTITY(1,1) NOT NULL,
	[FechaFacturaVenta] [date] NULL,
	[BrutoFacturaVenta] [real] NULL,
	[IvaFacturaVenta] [real] NULL,
	[TotalFacturaVenta] [real] NULL,
	[Anulada] [smallint] NULL,
	[IdCliente] [int] NOT NULL,
	[IdEmpleado] [int] NOT NULL,
 CONSTRAINT [PK_facturaventa_IdFacturaVenta] PRIMARY KEY CLUSTERED 
(
	[IdFacturaVenta] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[facturaventa_libro]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[facturaventa_libro](
	[IdFacturaVenta_Libro] [int] IDENTITY(1,1) NOT NULL,
	[Cantidad] [int] NOT NULL,
	[Precio] [real] NOT NULL,
	[Subtotal] [real] NOT NULL,
	[IdFacturaVenta] [int] NULL,
	[IdLibro] [int] NOT NULL,
 CONSTRAINT [PK_facturaventa_libro_IdFacturaVenta_Libro] PRIMARY KEY CLUSTERED 
(
	[IdFacturaVenta_Libro] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[genero]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[genero](
	[IdGenero] [int] IDENTITY(9,1) NOT NULL,
	[NombreGenero] [nvarchar](45) NOT NULL,
	[EstadoGenero] [smallint] NOT NULL,
 CONSTRAINT [PK_genero_IdGenero] PRIMARY KEY CLUSTERED 
(
	[IdGenero] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [genero$NombreGenero_UNIQUE] UNIQUE NONCLUSTERED 
(
	[NombreGenero] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[libro]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[libro](
	[IdLibro] [int] IDENTITY(9,1) NOT NULL,
	[TituloLibro] [nvarchar](45) NOT NULL,
	[EdicionLibro] [nvarchar](45) NULL,
	[AñoLibro] [int] NULL,
	[PrecioLibro] [real] NULL,
	[StockLibro] [int] NULL,
	[StockCritico] [int] NULL,
	[StockMax] [int] NULL,
	[EstadoLibro] [smallint] NULL,
	[CostoLibro] [real] NULL,
	[IdEditorialLibro] [int] NOT NULL,
	[IdAutorLibro] [int] NOT NULL,
	[IdGeneroLibro] [int] NOT NULL,
 CONSTRAINT [PK_libro_IdLibro] PRIMARY KEY CLUSTERED 
(
	[IdLibro] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[libroproveedor]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[libroproveedor](
	[IdLibroProveedor] [int] IDENTITY(6,1) NOT NULL,
	[IdLibro] [int] NOT NULL,
	[IdProveedor] [int] NOT NULL,
 CONSTRAINT [PK_libroproveedor_IdLibroProveedor] PRIMARY KEY CLUSTERED 
(
	[IdLibroProveedor] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[lineapedido]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[lineapedido](
	[idLineaPedido] [int] IDENTITY(1,1) NOT NULL,
	[CantidadPedido] [int] NOT NULL,
	[CantidadRecibida] [int] NOT NULL,
	[CostoLineaPedido] [real] NULL,
	[IdLibroCompra] [int] NOT NULL,
	[IdPedidoCompra] [int] NOT NULL,
 CONSTRAINT [PK_lineapedido_idLineaPedido] PRIMARY KEY CLUSTERED 
(
	[idLineaPedido] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[localidad]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[localidad](
	[IdLocalidad] [int] IDENTITY(20,1) NOT NULL,
	[NombreLocalidad] [nvarchar](45) NOT NULL,
	[IdProvincia] [int] NOT NULL,
 CONSTRAINT [PK_localidad_IdLocalidad] PRIMARY KEY CLUSTERED 
(
	[IdLocalidad] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[pedidocompra]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[pedidocompra](
	[idPedidoCompra] [int] IDENTITY(1,1) NOT NULL,
	[FechaPedido] [date] NOT NULL,
	[EstadoPedido] [nvarchar](20) NOT NULL,
	[TotalPedidoCompra] [real] NULL,
	[IdProveedorCompra] [int] NOT NULL,
 CONSTRAINT [PK_pedidocompra_idPedidoCompra] PRIMARY KEY CLUSTERED 
(
	[idPedidoCompra] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[proveedor]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[proveedor](
	[IdProveedor] [int] IDENTITY(4,1) NOT NULL,
	[CuitProveedor] [nvarchar](15) NOT NULL,
	[NombreProveedor] [nvarchar](45) NOT NULL,
	[DireccionProveedor] [nvarchar](45) NULL,
	[TelProveedor] [nvarchar](15) NULL,
	[EstadoProveedor] [smallint] NULL,
	[IdLocalidadProveedor] [int] NOT NULL,
 CONSTRAINT [PK_proveedor_IdProveedor] PRIMARY KEY CLUSTERED 
(
	[IdProveedor] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [proveedor$CuitProveedor_UNIQUE] UNIQUE NONCLUSTERED 
(
	[CuitProveedor] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[provincia]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[provincia](
	[IdProvincia] [int] IDENTITY(24,1) NOT NULL,
	[NombreProvincia] [nvarchar](45) NOT NULL,
 CONSTRAINT [PK_provincia_IdProvincia] PRIMARY KEY CLUSTERED 
(
	[IdProvincia] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [provincia$NombreProvincia_UNIQUE] UNIQUE NONCLUSTERED 
(
	[NombreProvincia] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[remitocompra]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[remitocompra](
	[IdRemitoCompra] [int] IDENTITY(1,1) NOT NULL,
	[NroRemitoCompra] [nvarchar](45) NOT NULL,
	[FechaRemitoCompra] [date] NOT NULL,
	[FechaRecepcionCompra] [date] NOT NULL,
	[IdProveedor] [int] NOT NULL,
	[IdPedidoCompra] [int] NOT NULL,
 CONSTRAINT [PK_remitocompra_IdRemitoCompra] PRIMARY KEY CLUSTERED 
(
	[IdRemitoCompra] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[remitocompra_libro]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[remitocompra_libro](
	[IdRemitoCompra_Libro] [int] IDENTITY(1,1) NOT NULL,
	[CantidadRemitoCompra] [int] NOT NULL,
	[CantidadRecibidaCompra] [int] NOT NULL,
	[IdRemitoCompra] [int] NOT NULL,
	[IdLibroCompra] [int] NOT NULL,
 CONSTRAINT [PK_remitocompra_libro_IdRemitoCompra_Libro] PRIMARY KEY CLUSTERED 
(
	[IdRemitoCompra_Libro] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[tipocliente]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[tipocliente](
	[IdTipoCliente] [int] IDENTITY(5,1) NOT NULL,
	[NombreTipoCliente] [nvarchar](45) NOT NULL,
	[IdTipoFacturaVenta] [int] NOT NULL,
 CONSTRAINT [PK_tipocliente_IdTipoCliente] PRIMARY KEY CLUSTERED 
(
	[IdTipoCliente] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [tipocliente$NombreTipoCliente_UNIQUE] UNIQUE NONCLUSTERED 
(
	[NombreTipoCliente] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[tipofacturaventa]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[tipofacturaventa](
	[IdTipoFacturaVenta] [int] IDENTITY(4,1) NOT NULL,
	[NombreTipoFacturaVenta] [nvarchar](45) NOT NULL,
	[DiscriminaIva] [smallint] NULL,
 CONSTRAINT [PK_tipofacturaventa_IdTipoFacturaVenta] PRIMARY KEY CLUSTERED 
(
	[IdTipoFacturaVenta] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [tipofacturaventa$NombreTipoFacturaVenta_UNIQUE] UNIQUE NONCLUSTERED 
(
	[NombreTipoFacturaVenta] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[tipousuario]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[tipousuario](
	[IdTipoUsuario] [int] IDENTITY(4,1) NOT NULL,
	[NombreTipoUsuario] [nvarchar](45) NOT NULL,
 CONSTRAINT [PK_tipousuario_IdTipoUsuario] PRIMARY KEY CLUSTERED 
(
	[IdTipoUsuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [tipousuario$NombreTipoUsuario_UNIQUE] UNIQUE NONCLUSTERED 
(
	[NombreTipoUsuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [libreria].[usuarios]    Script Date: 02/02/2020 16:03:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [libreria].[usuarios](
	[IdUsuario] [int] IDENTITY(2,1) NOT NULL,
	[Usuario] [nvarchar](45) NOT NULL,
	[Contraseña] [nvarchar](45) NOT NULL,
	[IdTipoUsuario] [int] NOT NULL,
 CONSTRAINT [PK_usuarios_IdUsuario] PRIMARY KEY CLUSTERED 
(
	[IdUsuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [usuarios$Usuario_UNIQUE] UNIQUE NONCLUSTERED 
(
	[Usuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Index [fk_Cliente_Localidad1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_Cliente_Localidad1_idx] ON [libreria].[cliente]
(
	[IdLocalidadCliente] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_Cliente_TipoCliente1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_Cliente_TipoCliente1_idx] ON [libreria].[cliente]
(
	[IdTipoCliente] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_Empleado_CategoriaEmpleado1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_Empleado_CategoriaEmpleado1_idx] ON [libreria].[empleado]
(
	[IdCategoriaEmpleado] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_Empleado_EstadoCivilEmpleado1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_Empleado_EstadoCivilEmpleado1_idx] ON [libreria].[empleado]
(
	[IdEstadoCivilEmpleado] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_Empleado_Localidad1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_Empleado_Localidad1_idx] ON [libreria].[empleado]
(
	[IdLocalidad] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_Empleado_Usuarios1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_Empleado_Usuarios1_idx] ON [libreria].[empleado]
(
	[IdUsuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_venta_cliente1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_venta_cliente1_idx] ON [libreria].[facturaventa]
(
	[IdCliente] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_venta_empleado1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_venta_empleado1_idx] ON [libreria].[facturaventa]
(
	[IdEmpleado] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_FacturaVenta_Libro_FacturaVenta1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_FacturaVenta_Libro_FacturaVenta1_idx] ON [libreria].[facturaventa_libro]
(
	[IdFacturaVenta] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_venta_has_libro_libro1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_venta_has_libro_libro1_idx] ON [libreria].[facturaventa_libro]
(
	[IdLibro] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_Libro_Autor1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_Libro_Autor1_idx] ON [libreria].[libro]
(
	[IdAutorLibro] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_libro_editorial1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_libro_editorial1_idx] ON [libreria].[libro]
(
	[IdEditorialLibro] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_Libro_Genero1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_Libro_Genero1_idx] ON [libreria].[libro]
(
	[IdGeneroLibro] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_Libro_has_Proveedor_Libro1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_Libro_has_Proveedor_Libro1_idx] ON [libreria].[libroproveedor]
(
	[IdLibro] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_Libro_has_Proveedor_Proveedor1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_Libro_has_Proveedor_Proveedor1_idx] ON [libreria].[libroproveedor]
(
	[IdProveedor] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_lineaPedido_Libro1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_lineaPedido_Libro1_idx] ON [libreria].[lineapedido]
(
	[IdLibroCompra] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_lineaPedido_pedidocompra1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_lineaPedido_pedidocompra1_idx] ON [libreria].[lineapedido]
(
	[IdPedidoCompra] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_Localidad_provincia1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_Localidad_provincia1_idx] ON [libreria].[localidad]
(
	[IdProvincia] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_pedidocompra_Proveedor1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_pedidocompra_Proveedor1_idx] ON [libreria].[pedidocompra]
(
	[IdProveedorCompra] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_Proveedor_Localidad1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_Proveedor_Localidad1_idx] ON [libreria].[proveedor]
(
	[IdLocalidadProveedor] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_RemitoCompra_pedidocompra1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_RemitoCompra_pedidocompra1_idx] ON [libreria].[remitocompra]
(
	[IdPedidoCompra] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_RemitoCompra_Proveedor1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_RemitoCompra_Proveedor1_idx] ON [libreria].[remitocompra]
(
	[IdProveedor] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_RemitoCompra_Libro_Libro1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_RemitoCompra_Libro_Libro1_idx] ON [libreria].[remitocompra_libro]
(
	[IdLibroCompra] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_RemitoCompra_Libro_RemitoCompra1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_RemitoCompra_Libro_RemitoCompra1_idx] ON [libreria].[remitocompra_libro]
(
	[IdRemitoCompra] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_TipoCliente_TipoFacturaVenta1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_TipoCliente_TipoFacturaVenta1_idx] ON [libreria].[tipocliente]
(
	[IdTipoFacturaVenta] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [fk_Usuarios_TipoUsuario1_idx]    Script Date: 02/02/2020 16:03:04 ******/
CREATE NONCLUSTERED INDEX [fk_Usuarios_TipoUsuario1_idx] ON [libreria].[usuarios]
(
	[IdTipoUsuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [libreria].[autor] ADD  DEFAULT ((1)) FOR [EstadoAutor]
GO
ALTER TABLE [libreria].[categoriaempleado] ADD  DEFAULT ((0)) FOR [SueldoBasicoCategoria]
GO
ALTER TABLE [libreria].[cliente] ADD  DEFAULT (NULL) FOR [DniCliente]
GO
ALTER TABLE [libreria].[cliente] ADD  DEFAULT (NULL) FOR [CuitCliente]
GO
ALTER TABLE [libreria].[cliente] ADD  DEFAULT (NULL) FOR [TelefonoCliente]
GO
ALTER TABLE [libreria].[cliente] ADD  DEFAULT (NULL) FOR [EmailCliente]
GO
ALTER TABLE [libreria].[cliente] ADD  DEFAULT ((1)) FOR [EstadoCliente]
GO
ALTER TABLE [libreria].[editorial] ADD  DEFAULT (NULL) FOR [DireccionEditorial]
GO
ALTER TABLE [libreria].[editorial] ADD  DEFAULT (NULL) FOR [TelefonoEditorial]
GO
ALTER TABLE [libreria].[editorial] ADD  DEFAULT ((1)) FOR [EstadoEditorial]
GO
ALTER TABLE [libreria].[empleado] ADD  DEFAULT (NULL) FOR [DomicilioEmpleado]
GO
ALTER TABLE [libreria].[empleado] ADD  DEFAULT (NULL) FOR [TelefonoEmpleado]
GO
ALTER TABLE [libreria].[empleado] ADD  DEFAULT (NULL) FOR [FechaIngreso]
GO
ALTER TABLE [libreria].[empleado] ADD  DEFAULT (NULL) FOR [IdUsuario]
GO
ALTER TABLE [libreria].[empleado] ADD  DEFAULT ((1)) FOR [EstadoEmpleado]
GO
ALTER TABLE [libreria].[facturaventa] ADD  DEFAULT (NULL) FOR [FechaFacturaVenta]
GO
ALTER TABLE [libreria].[facturaventa] ADD  DEFAULT ((0)) FOR [BrutoFacturaVenta]
GO
ALTER TABLE [libreria].[facturaventa] ADD  DEFAULT ((0)) FOR [IvaFacturaVenta]
GO
ALTER TABLE [libreria].[facturaventa] ADD  DEFAULT ((0)) FOR [TotalFacturaVenta]
GO
ALTER TABLE [libreria].[facturaventa] ADD  DEFAULT ((0)) FOR [Anulada]
GO
ALTER TABLE [libreria].[facturaventa_libro] ADD  DEFAULT ((1)) FOR [Cantidad]
GO
ALTER TABLE [libreria].[facturaventa_libro] ADD  DEFAULT ((0)) FOR [Precio]
GO
ALTER TABLE [libreria].[facturaventa_libro] ADD  DEFAULT ((0)) FOR [Subtotal]
GO
ALTER TABLE [libreria].[facturaventa_libro] ADD  DEFAULT (NULL) FOR [IdFacturaVenta]
GO
ALTER TABLE [libreria].[genero] ADD  DEFAULT ((1)) FOR [EstadoGenero]
GO
ALTER TABLE [libreria].[libro] ADD  DEFAULT (NULL) FOR [EdicionLibro]
GO
ALTER TABLE [libreria].[libro] ADD  DEFAULT (NULL) FOR [AñoLibro]
GO
ALTER TABLE [libreria].[libro] ADD  DEFAULT ((0)) FOR [PrecioLibro]
GO
ALTER TABLE [libreria].[libro] ADD  DEFAULT (NULL) FOR [StockLibro]
GO
ALTER TABLE [libreria].[libro] ADD  DEFAULT ((5)) FOR [StockCritico]
GO
ALTER TABLE [libreria].[libro] ADD  DEFAULT ((20)) FOR [StockMax]
GO
ALTER TABLE [libreria].[libro] ADD  DEFAULT ((1)) FOR [EstadoLibro]
GO
ALTER TABLE [libreria].[libro] ADD  DEFAULT (NULL) FOR [CostoLibro]
GO
ALTER TABLE [libreria].[lineapedido] ADD  DEFAULT ((0)) FOR [CantidadPedido]
GO
ALTER TABLE [libreria].[lineapedido] ADD  DEFAULT ((0)) FOR [CantidadRecibida]
GO
ALTER TABLE [libreria].[lineapedido] ADD  DEFAULT ((0)) FOR [CostoLineaPedido]
GO
ALTER TABLE [libreria].[pedidocompra] ADD  DEFAULT (NULL) FOR [TotalPedidoCompra]
GO
ALTER TABLE [libreria].[proveedor] ADD  DEFAULT (NULL) FOR [DireccionProveedor]
GO
ALTER TABLE [libreria].[proveedor] ADD  DEFAULT (NULL) FOR [TelProveedor]
GO
ALTER TABLE [libreria].[proveedor] ADD  DEFAULT ((1)) FOR [EstadoProveedor]
GO
ALTER TABLE [libreria].[remitocompra_libro] ADD  DEFAULT ((0)) FOR [CantidadRemitoCompra]
GO
ALTER TABLE [libreria].[remitocompra_libro] ADD  DEFAULT ((0)) FOR [CantidadRecibidaCompra]
GO
ALTER TABLE [libreria].[tipofacturaventa] ADD  DEFAULT ((0)) FOR [DiscriminaIva]
GO
ALTER TABLE [libreria].[cliente]  WITH NOCHECK ADD  CONSTRAINT [cliente$fk_Cliente_Localidad1] FOREIGN KEY([IdLocalidadCliente])
REFERENCES [libreria].[localidad] ([IdLocalidad])
GO
ALTER TABLE [libreria].[cliente] CHECK CONSTRAINT [cliente$fk_Cliente_Localidad1]
GO
ALTER TABLE [libreria].[cliente]  WITH NOCHECK ADD  CONSTRAINT [cliente$fk_Cliente_TipoCliente1] FOREIGN KEY([IdTipoCliente])
REFERENCES [libreria].[tipocliente] ([IdTipoCliente])
GO
ALTER TABLE [libreria].[cliente] CHECK CONSTRAINT [cliente$fk_Cliente_TipoCliente1]
GO
ALTER TABLE [libreria].[empleado]  WITH NOCHECK ADD  CONSTRAINT [empleado$fk_Empleado_CategoriaEmpleado1] FOREIGN KEY([IdCategoriaEmpleado])
REFERENCES [libreria].[categoriaempleado] ([IdCategoriaEmpleado])
GO
ALTER TABLE [libreria].[empleado] CHECK CONSTRAINT [empleado$fk_Empleado_CategoriaEmpleado1]
GO
ALTER TABLE [libreria].[empleado]  WITH NOCHECK ADD  CONSTRAINT [empleado$fk_Empleado_EstadoCivilEmpleado1] FOREIGN KEY([IdEstadoCivilEmpleado])
REFERENCES [libreria].[estadocivilempleado] ([IdEstadoCivilEmpleado])
GO
ALTER TABLE [libreria].[empleado] CHECK CONSTRAINT [empleado$fk_Empleado_EstadoCivilEmpleado1]
GO
ALTER TABLE [libreria].[empleado]  WITH NOCHECK ADD  CONSTRAINT [empleado$fk_Empleado_Localidad1] FOREIGN KEY([IdLocalidad])
REFERENCES [libreria].[localidad] ([IdLocalidad])
GO
ALTER TABLE [libreria].[empleado] CHECK CONSTRAINT [empleado$fk_Empleado_Localidad1]
GO
ALTER TABLE [libreria].[empleado]  WITH NOCHECK ADD  CONSTRAINT [empleado$fk_Empleado_Usuarios1] FOREIGN KEY([IdUsuario])
REFERENCES [libreria].[usuarios] ([IdUsuario])
GO
ALTER TABLE [libreria].[empleado] CHECK CONSTRAINT [empleado$fk_Empleado_Usuarios1]
GO
ALTER TABLE [libreria].[facturaventa]  WITH NOCHECK ADD  CONSTRAINT [facturaventa$fk_venta_cliente1] FOREIGN KEY([IdCliente])
REFERENCES [libreria].[cliente] ([IdCliente])
GO
ALTER TABLE [libreria].[facturaventa] CHECK CONSTRAINT [facturaventa$fk_venta_cliente1]
GO
ALTER TABLE [libreria].[facturaventa]  WITH NOCHECK ADD  CONSTRAINT [facturaventa$fk_venta_empleado1] FOREIGN KEY([IdEmpleado])
REFERENCES [libreria].[empleado] ([IdEmpleado])
GO
ALTER TABLE [libreria].[facturaventa] CHECK CONSTRAINT [facturaventa$fk_venta_empleado1]
GO
ALTER TABLE [libreria].[facturaventa_libro]  WITH NOCHECK ADD  CONSTRAINT [facturaventa_libro$fk_FacturaVenta_Libro_FacturaVenta1] FOREIGN KEY([IdFacturaVenta])
REFERENCES [libreria].[facturaventa] ([IdFacturaVenta])
GO
ALTER TABLE [libreria].[facturaventa_libro] CHECK CONSTRAINT [facturaventa_libro$fk_FacturaVenta_Libro_FacturaVenta1]
GO
ALTER TABLE [libreria].[facturaventa_libro]  WITH NOCHECK ADD  CONSTRAINT [facturaventa_libro$fk_venta_has_libro_libro1] FOREIGN KEY([IdLibro])
REFERENCES [libreria].[libro] ([IdLibro])
GO
ALTER TABLE [libreria].[facturaventa_libro] CHECK CONSTRAINT [facturaventa_libro$fk_venta_has_libro_libro1]
GO
ALTER TABLE [libreria].[libro]  WITH NOCHECK ADD  CONSTRAINT [libro$fk_Libro_Autor1] FOREIGN KEY([IdAutorLibro])
REFERENCES [libreria].[autor] ([IdAutor])
GO
ALTER TABLE [libreria].[libro] CHECK CONSTRAINT [libro$fk_Libro_Autor1]
GO
ALTER TABLE [libreria].[libro]  WITH NOCHECK ADD  CONSTRAINT [libro$fk_libro_editorial1] FOREIGN KEY([IdEditorialLibro])
REFERENCES [libreria].[editorial] ([IdEditorial])
GO
ALTER TABLE [libreria].[libro] CHECK CONSTRAINT [libro$fk_libro_editorial1]
GO
ALTER TABLE [libreria].[libro]  WITH NOCHECK ADD  CONSTRAINT [libro$fk_Libro_Genero1] FOREIGN KEY([IdGeneroLibro])
REFERENCES [libreria].[genero] ([IdGenero])
GO
ALTER TABLE [libreria].[libro] CHECK CONSTRAINT [libro$fk_Libro_Genero1]
GO
ALTER TABLE [libreria].[libroproveedor]  WITH NOCHECK ADD  CONSTRAINT [libroproveedor$fk_Libro_has_Proveedor_Libro1] FOREIGN KEY([IdLibro])
REFERENCES [libreria].[libro] ([IdLibro])
GO
ALTER TABLE [libreria].[libroproveedor] CHECK CONSTRAINT [libroproveedor$fk_Libro_has_Proveedor_Libro1]
GO
ALTER TABLE [libreria].[libroproveedor]  WITH NOCHECK ADD  CONSTRAINT [libroproveedor$fk_Libro_has_Proveedor_Proveedor1] FOREIGN KEY([IdProveedor])
REFERENCES [libreria].[proveedor] ([IdProveedor])
GO
ALTER TABLE [libreria].[libroproveedor] CHECK CONSTRAINT [libroproveedor$fk_Libro_has_Proveedor_Proveedor1]
GO
ALTER TABLE [libreria].[lineapedido]  WITH NOCHECK ADD  CONSTRAINT [lineapedido$fk_lineaPedido_Libro1] FOREIGN KEY([IdLibroCompra])
REFERENCES [libreria].[libro] ([IdLibro])
GO
ALTER TABLE [libreria].[lineapedido] CHECK CONSTRAINT [lineapedido$fk_lineaPedido_Libro1]
GO
ALTER TABLE [libreria].[lineapedido]  WITH NOCHECK ADD  CONSTRAINT [lineapedido$fk_lineaPedido_pedidocompra1] FOREIGN KEY([IdPedidoCompra])
REFERENCES [libreria].[pedidocompra] ([idPedidoCompra])
GO
ALTER TABLE [libreria].[lineapedido] CHECK CONSTRAINT [lineapedido$fk_lineaPedido_pedidocompra1]
GO
ALTER TABLE [libreria].[localidad]  WITH NOCHECK ADD  CONSTRAINT [localidad$fk_Localidad_provincia1] FOREIGN KEY([IdProvincia])
REFERENCES [libreria].[provincia] ([IdProvincia])
GO
ALTER TABLE [libreria].[localidad] CHECK CONSTRAINT [localidad$fk_Localidad_provincia1]
GO
ALTER TABLE [libreria].[pedidocompra]  WITH NOCHECK ADD  CONSTRAINT [pedidocompra$fk_pedidocompra_Proveedor1] FOREIGN KEY([IdProveedorCompra])
REFERENCES [libreria].[proveedor] ([IdProveedor])
GO
ALTER TABLE [libreria].[pedidocompra] CHECK CONSTRAINT [pedidocompra$fk_pedidocompra_Proveedor1]
GO
ALTER TABLE [libreria].[proveedor]  WITH NOCHECK ADD  CONSTRAINT [proveedor$fk_Proveedor_Localidad1] FOREIGN KEY([IdLocalidadProveedor])
REFERENCES [libreria].[localidad] ([IdLocalidad])
GO
ALTER TABLE [libreria].[proveedor] CHECK CONSTRAINT [proveedor$fk_Proveedor_Localidad1]
GO
ALTER TABLE [libreria].[remitocompra]  WITH NOCHECK ADD  CONSTRAINT [remitocompra$fk_RemitoCompra_pedidocompra1] FOREIGN KEY([IdPedidoCompra])
REFERENCES [libreria].[pedidocompra] ([idPedidoCompra])
GO
ALTER TABLE [libreria].[remitocompra] CHECK CONSTRAINT [remitocompra$fk_RemitoCompra_pedidocompra1]
GO
ALTER TABLE [libreria].[remitocompra]  WITH NOCHECK ADD  CONSTRAINT [remitocompra$fk_RemitoCompra_Proveedor1] FOREIGN KEY([IdProveedor])
REFERENCES [libreria].[proveedor] ([IdProveedor])
GO
ALTER TABLE [libreria].[remitocompra] CHECK CONSTRAINT [remitocompra$fk_RemitoCompra_Proveedor1]
GO
ALTER TABLE [libreria].[remitocompra_libro]  WITH NOCHECK ADD  CONSTRAINT [remitocompra_libro$fk_RemitoCompra_Libro_Libro1] FOREIGN KEY([IdLibroCompra])
REFERENCES [libreria].[libro] ([IdLibro])
GO
ALTER TABLE [libreria].[remitocompra_libro] CHECK CONSTRAINT [remitocompra_libro$fk_RemitoCompra_Libro_Libro1]
GO
ALTER TABLE [libreria].[remitocompra_libro]  WITH NOCHECK ADD  CONSTRAINT [remitocompra_libro$fk_RemitoCompra_Libro_RemitoCompra1] FOREIGN KEY([IdRemitoCompra])
REFERENCES [libreria].[remitocompra] ([IdRemitoCompra])
GO
ALTER TABLE [libreria].[remitocompra_libro] CHECK CONSTRAINT [remitocompra_libro$fk_RemitoCompra_Libro_RemitoCompra1]
GO
ALTER TABLE [libreria].[tipocliente]  WITH NOCHECK ADD  CONSTRAINT [tipocliente$fk_TipoCliente_TipoFacturaVenta1] FOREIGN KEY([IdTipoFacturaVenta])
REFERENCES [libreria].[tipofacturaventa] ([IdTipoFacturaVenta])
GO
ALTER TABLE [libreria].[tipocliente] CHECK CONSTRAINT [tipocliente$fk_TipoCliente_TipoFacturaVenta1]
GO
ALTER TABLE [libreria].[usuarios]  WITH NOCHECK ADD  CONSTRAINT [usuarios$fk_Usuarios_TipoUsuario1] FOREIGN KEY([IdTipoUsuario])
REFERENCES [libreria].[tipousuario] ([IdTipoUsuario])
GO
ALTER TABLE [libreria].[usuarios] CHECK CONSTRAINT [usuarios$fk_Usuarios_TipoUsuario1]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.autor' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'autor'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.categoriaempleado' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'categoriaempleado'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.cliente' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'cliente'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.editorial' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'editorial'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.empleado' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'empleado'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.estadocivilempleado' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'estadocivilempleado'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.facturaventa' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'facturaventa'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.facturaventa_libro' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'facturaventa_libro'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.genero' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'genero'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.libro' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'libro'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.libroproveedor' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'libroproveedor'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.lineapedido' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'lineapedido'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.localidad' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'localidad'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.pedidocompra' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'pedidocompra'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.proveedor' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'proveedor'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.provincia' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'provincia'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.remitocompra' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'remitocompra'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.remitocompra_libro' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'remitocompra_libro'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.tipocliente' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'tipocliente'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.tipofacturaventa' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'tipofacturaventa'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.tipousuario' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'tipousuario'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'libreria.usuarios' , @level0type=N'SCHEMA',@level0name=N'libreria', @level1type=N'TABLE',@level1name=N'usuarios'
GO
USE [master]
GO
ALTER DATABASE [libreria] SET  READ_WRITE 
GO
