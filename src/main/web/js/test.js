alphabet = 'qwertyuiopasdfghjklzxcvbnm'
    .split('').sort()
specSymbols = '!@#$%^&*()_+=-{}[]/,.`"\''
    .split('')
figures = '1234567890'.split('')
rand = (min, max) => Math
    .round(Math.random() * (max - min) + min)

function iterateArray(array, interval, isRand) {
    i = 0
    setInterval(() => {
        if (isRand)
            console.log(array[rand(0, array.length - 1)])
        else
            console.log(array[i++])
        if (i === array.length - 1)
            i = 0
    }, interval)
}

