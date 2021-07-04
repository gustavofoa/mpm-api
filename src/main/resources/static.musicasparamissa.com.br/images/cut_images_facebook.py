from PIL import Image, ImageOps, ImageDraw
import glob, os

size = 80, 80

for infile in glob.glob("static/images/diasLiturgicos/*.*"):
    name = os.path.basename(infile)
    file, ext = os.path.splitext(infile)
    im = Image.open(infile)

    output = ImageOps.fit(im, size, centering=(0.5, 0.5))

    output.save("static/images/diasLiturgicos/80x80/" + name)
